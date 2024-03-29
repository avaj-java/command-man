package jaemisseo.man.configuration.properties

import jaemisseo.man.configuration.annotation.type.Bean
import jaemisseo.man.configuration.exception.OutOfArgumentException
import jaemisseo.man.FileMan
import jaemisseo.man.PropMan
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import java.security.CodeSource

/**
 * Created by sujkim on 2017-03-29.
 */
@Bean
class PropertiesGenerator extends jaemisseo.man.util.PropertiesGenerator{

    final Logger logger = LoggerFactory.getLogger(this.getClass());

    Map<String, PropMan> dataMap = [:]


    PropMan defaultProperties
    PropMan programProperties
    PropMan externalProperties



    //Generate Properties Perspective Map
    Map genApplicationPropertiesValueMap(String[] args){
        Map valueListMap = super.genPropertyValueMap(args)

        //Check
//        println "- Check Arguments."
//        propertiesValueMap.each{
//            println "${it.key}=${it.value}"
//        }
//        println ""
        return valueListMap
    }


    //Generate Properties Perspective Map
    Map genApplicationPropertiesValueMap(Map terminalPropertiesValueMap, Map<Class, List> lowerTaskNameAndValueProtocolListMap){
        Map applicationPropertyValueMap = [
            ''      : [],
            '--'    : []
        ]

        terminalPropertiesValueMap.each{ String proppertyName, def propertyValue ->
            if (propertyValue instanceof List){

                // -KEY VALUE VALUE ..
                List valueProtocolList = lowerTaskNameAndValueProtocolListMap[proppertyName.toLowerCase()]
                if (valueProtocolList){
                    if (valueProtocolList.size() >= propertyValue.size()){
                        propertyValue.eachWithIndex{ def value, int i ->
                            String propName = valueProtocolList[i]
                            applicationPropertyValueMap[propName] = parseValue(value)
                        }
                    }else{
                        int size = valueProtocolList.size()
                        int maxPreviewCount = 3
                        boolean sizeIsOverPreview = size > maxPreviewCount
                        int fromPreviewIndex = 0
                        int toPreviewIndex = sizeIsOverPreview ? maxPreviewCount -1 : size -1
                        List<?> previewProtocolList = valueProtocolList[fromPreviewIndex..toPreviewIndex]
                        throw new OutOfArgumentException("So Many arguments!. Check ${proppertyName}'s Arguments. - Size: ${size}, Protocols: ${previewProtocolList} ${sizeIsOverPreview?' ... ':''}")
                    }
                }


                if (proppertyName){
                    // --VALUE
                    if (proppertyName == '--'){
                        applicationPropertyValueMap['--'] = propertyValue

                    // -KEY
                    }else{
                        applicationPropertyValueMap[proppertyName] = parseValue(propertyValue) ?: true
                    }

                // VALUE VALUE ..
                }else{
                    applicationPropertyValueMap[''] = propertyValue
                }

            }else{
                // -KEY=VALUE
                applicationPropertyValueMap[proppertyName] = parseValue(propertyValue)
            }
        }

        return applicationPropertyValueMap
    }

    def parseValue(def value){
        if (['true', 'True', 'TRUE'].contains(value))
            return true
        if (['false', 'False', 'FALSE'].contains(value))
            return false
        return value
    }

    static String replacePathWinToLin(String path){
        return path?.replace("\\", "/")
    }


    static PropMan genDefaultProperties(){
        PropMan propman = new PropMan([
                'os.name': System.getProperty('os.name'),
                'os.version': System.getProperty('os.version'),
                'user.name': System.getProperty('user.name'),
                'java.version': System.getProperty('java.version'),
                'java.home': replacePathWinToLin( System.getProperty('java.home') ),
                'user.dir': replacePathWinToLin( System.getProperty('user.dir') ),
                'user.home': replacePathWinToLin( System.getProperty('user.home') ),
        ])
        /** maybe programProperties **/
        propman.merge([
            // installer.jar path
            'lib.dir': replacePathWinToLin( new PropertiesGenerator().getThisAppFile()?.getParentFile()?.getPath() ) ?: '',
            'lib.path': replacePathWinToLin( new PropertiesGenerator().getThisAppFile()?.getPath() ) ?: '',
            'lib.version': FileMan.getFileFromResourceWithoutException('.version')?.text ?: '',
            'lib.compiler': FileMan.getFileFromResourceWithoutException('.compiler')?.text ?: '',
            'lib.build.date': FileMan.getFileFromResourceWithoutException('.date')?.text ?: '',
            'product.name': FileMan.getFileFromResourceWithoutException('.productname')?.text?.trim() ?: '',
            'product.version': FileMan.getFileFromResourceWithoutException('.productversion')?.text?.trim() ?: '',
        ])
        return propman
    }

    static PropMan gen(String filePath){
        return new PropMan().readFile(filePath)
    }

    static PropMan genResource(String resourcePath){
        return new PropMan().readResource(resourcePath)
    }



    boolean containsKey(String key){
        return dataMap.containsKey(key)
    }



    /**
     *
     */
    PropMan get(String key){
        return dataMap[key]
    }

    PropertiesGenerator add(String key, String filePath){
        dataMap[key] = new PropMan().readFile(filePath)
        return this
    }

    PropertiesGenerator addResource(String key, String resourcePath){
        dataMap[key] = new PropMan().readResource(resourcePath)
        return this
    }

    PropMan genSingletonPropManFromFileSystem(String key, String filePath){
        if (!dataMap.containsKey(key))
            add(key, filePath)
        return dataMap[key]
    }

    PropMan genSingletonPropManFromResource(String key, String filePath){
        if (!dataMap.containsKey(key))
            addResource(key, filePath)
        return dataMap[key]
    }

    /*****
     * Get
     *****/
    List<String> getNoDashOptionList(){
        return getExternalProperties().get('') ?: []
    }

    List<String> getDashOptionList(){
        return getExternalProperties().properties.findAll{
            it.key != '' && it.key != '--'
        }.collect{
            it.key
        }
    }

    List<String> getDashDashOptionList(){
        return getExternalProperties().get('--') ?: []
    }

    Map<String, String> getDashOptions(){
        return getExternalProperties().properties.findAll{
            it.key != '' && it.key != '--'
        }
    }

    Object getValueFromDashOption(String optionName){
        return getExternalProperties().properties.get(optionName)
    }


    /*****
     * Has
     *****/
    boolean hasNoDashOption(String optionName){
        return getNoDashOptionList()?.contains(optionName)
    }

    boolean hasDashOption(String optionName){
        return getDashOptionList()?.contains(optionName)
    }

    boolean hasDashOption(String optionName, Object value){
        String compareValue = value.toString()
        return !!getExternalProperties().properties.findAll{
            it.key != '' && it.key != '--' && it.key == optionName && it.value.toString() == compareValue
        }
    }

    boolean hasDashDashOption(String optionName){
        return getDashDashOptionList()?.contains(optionName)
    }


    /**
     *
     */
    PropertiesGenerator makeDefaultProperties(){
        defaultProperties = genDefaultProperties()
        return this
    }

    PropertiesGenerator makeProgramProperties(String[] args){
        Map argsMap = genApplicationPropertiesValueMap(args)
        programProperties = new PropMan()
        programProperties.set('program.args', args.join(' '))
        String normalProperties = argsMap.findAll{ it.key != '' && it.key != '--' }.collect{ "-${it.key}=${it.value}" }.join(' ')
        String dashDashFlagProperties = argsMap['--'].collect{ "--${it}" }.join(' ')
        String argsExceptCommand = normalProperties+ ' ' +dashDashFlagProperties
        programProperties.set('program.args.except.command', argsExceptCommand)
        return this
    }

    PropertiesGenerator makeExternalProperties(String[] args, Map<Class, List> valueProtocolListMap){
        Map argsMap = genApplicationPropertiesValueMap(args)
        if (!externalProperties)
            externalProperties = new PropMan()
        if (valueProtocolListMap){
            Map propertiesValueMap = genApplicationPropertiesValueMap(argsMap, valueProtocolListMap)
            externalProperties.merge(propertiesValueMap)
        }else{
            externalProperties.merge(argsMap)
        }
        return this
    }



    PropMan getExternalProperties(){
        return externalProperties
    }

    PropMan getDefaultProperties(){
        return defaultProperties
    }

    PropMan getProgramProperties(){
        return programProperties
    }



    File getThisAppFile(){
        File thisAppFile
        CodeSource src = this.getClass().getProtectionDomain().getCodeSource()
        if (src)
            thisAppFile = new File( src.getLocation().toURI().getPath() )
        return thisAppFile
    }

}
