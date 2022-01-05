package osp.leobert.android.gdcollector

import com.google.auto.service.AutoService
import com.squareup.javapoet.*
import org.greenrobot.greendao.annotation.Generated
import org.greenrobot.greendao.generator.DaoUtil
import osp.leobert.android.gdcollector.greendao.Consts
import osp.leobert.android.gdcollector.greendao.GreenDaoEntity
import javax.annotation.processing.AbstractProcessor
import javax.annotation.processing.ProcessingEnvironment
import javax.annotation.processing.Processor
import javax.annotation.processing.RoundEnvironment
import javax.lang.model.SourceVersion
import javax.lang.model.element.Element
import javax.lang.model.element.Modifier
import javax.lang.model.element.TypeElement
import javax.lang.model.type.TypeMirror
import javax.lang.model.util.ElementKindVisitor6
import javax.lang.model.util.SimpleTypeVisitor6

/**
 * Created by leobert on 2022/1/4.
 */
@AutoService(Processor::class)
class CollectorProcessor : AbstractProcessor() {

    private lateinit var processEnv: ProcessingEnvironment

    private lateinit var logger: Logger

    private lateinit var daoPackage: String
    private lateinit var moduleName: String

    override fun getSupportedAnnotationTypes(): MutableSet<String> {
        return mutableSetOf(Consts.entityNotationFullPath)
    }

    override fun getSupportedSourceVersion(): SourceVersion {
        return SourceVersion.latest()
    }

    override fun getSupportedOptions(): MutableSet<String> {
        return mutableSetOf(Consts.OPT_DAOPACKAGE, Consts.OPT_MODULE)
    }

    override fun init(processingEnv: ProcessingEnvironment?) {
        super.init(processingEnv)

        this.processEnv = requireNotNull(processingEnv)

        logger = Logger(processEnv.messager)

        val options: Map<String, String> = processEnv.options
        daoPackage = options[Consts.OPT_DAOPACKAGE] ?: ""
        moduleName = options[Consts.OPT_MODULE] ?: ""

    }

    override fun process(annotations: MutableSet<out TypeElement>?, roundEnv: RoundEnvironment?): Boolean {
        annotations?.takeIf { it.isNotEmpty() } ?: return false
        roundEnv ?: return false


        val entityNotation = processEnv.elementUtils.getTypeElement(Consts.entityNotationFullPath)
        if (entityNotation == null) {
            logger.error("无法获取${Consts.entityNotationFullPath},检查项目依赖")
            return false
        }

        val elementsNotated = roundEnv.getElementsAnnotatedWith(entityNotation)

        val checkedEntities = elementsNotated?.map { element ->
            element.accept(object : ElementKindVisitor6<TypeElement, Void?>() {
                override fun visitTypeAsClass(e: TypeElement?, p: Void?): TypeElement {
                    logger.info("find ${e.toString()} as class type")
                    return requireNotNull(e)
                }

                override fun defaultAction(e: Element?, p: Void?): TypeElement {
                    logger.error("check ${e.toString()},can not handle as class type!")
                    return super.defaultAction(e, p)
                }

            }, null)

        }?.filterNotNull()?.filter {
            val checked = checkIfHandledEntity(it)
            if (!checked) {
                logger.info("check $it, doesn't have '@Generate' missing dao-generate or kt class? ")
            }
            checked
        } ?: return false

        val entities = checkedEntities.map {
            GreenDaoEntity(
                entityElement = it,
                entityClzName = it.simpleName.toString(),
                daoPkg = daoPackage.takeIf { daoPackage -> daoPackage.isNotEmpty() }
                    ?: DaoUtil.getPackageFromFullyQualified(it.qualifiedName.toString())
            )
        }

        //校验增加TEMP的表是否会冲突
        entities.forEach { entity ->
            val tempName = entity.tableName + "_TEMP"

            val crashEntity = entities.find { it.tableName == tempName }
            if (crashEntity != null) {

                val error = "when create temp table for ${entity.entityName}, will match ${crashEntity.entityName}"
                logger.error(error)
                throw IllegalStateException(error)
            }
        }


        //生成类
        //生成返回dao的方法
        val listTypeElement = processEnv.elementUtils.getTypeElement("java.util.List")
        val clzTypeElement = processEnv.elementUtils.getTypeElement("java.lang.Class")


        val returnType = processEnv.typeUtils.getDeclaredType(listTypeElement, processEnv.typeUtils.getDeclaredType(clzTypeElement))


        val methodSpecGetAllMigrated = MethodSpec.methodBuilder("getAllMigratedDaos")
            .addJavadoc("return all Daos can found in this module")
            .addModifiers(Modifier.PUBLIC, Modifier.STATIC)
            .returns(TypeName.get(returnType))
            .apply {
                val ret = "return java.util.Arrays.asList(\r\n" + entities.joinToString(separator = ",\r\n") {
                    "    " + it.fullDaoPkg + ".class"
                } + "\r\n);"

                val codeSpec = CodeBlock.builder()
                    .add(ret)
                    .build()
                this.addCode(codeSpec)
            }

        logger.info(methodSpecGetAllMigrated.build().toString())

        val typeSpec = TypeSpec.classBuilder("${moduleName}DaoCollector")
            .addJavadoc("auto-generated")
            .addModifiers(Modifier.PUBLIC)
            .addMethod(methodSpecGetAllMigrated.build())
            .build()

        val fileSpec = JavaFile.builder(daoPackage, typeSpec).build()
        fileSpec.writeTo(processEnv.filer)

        return false
    }

    private fun checkIfHandledEntity(element: TypeElement): Boolean {
        return element.enclosedElements.find {
            it.getAnnotation(Generated::class.java) != null
        } != null
    }

    private abstract class CastingTypeVisitor<T> constructor(private val label: String) : SimpleTypeVisitor6<T, Void?>() {
        override fun defaultAction(e: TypeMirror, v: Void?): T {
            throw IllegalArgumentException("$e does not represent a $label")
        }
    }
}