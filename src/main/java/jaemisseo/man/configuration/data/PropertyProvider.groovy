package jaemisseo.man.configuration.data

import jaemisseo.man.configuration.LogGenerator
import jaemisseo.man.configuration.PropertiesGenerator
import jaemisseo.man.configuration.annotation.method.Init
import jaemisseo.man.configuration.annotation.method.Filter
import jaemisseo.man.configuration.annotation.type.Data
import jaemisseo.man.FileMan
import jaemisseo.man.PropMan
import jaemisseo.man.util.Util

/**
 * Created by sujkim on 2017-06-20.
 */
@Data
class PropertyProvider {

    PropMan propman
    String propertyPrefix
    PropertiesGenerator propGen
    LogGenerator logGen

    @Init
    void init(){
        this.propman = propGen.getDefaultProperties()
    }

    void printVersion(){
        logGen.logVersion(propGen.getDefaultProperties())
    }

    void printSystem(){
        logGen.logSystem(propGen.getDefaultProperties())
    }
    
    void shift(String name){
        this.propman = propGen.get(name)
    }

    void shift(String name, String propertyPrefix){
        this.propman = propGen.get(name)
        this.propertyPrefix = propertyPrefix
    }


    boolean checkCondition(String propertyPrefix){
        def conditionIfObj = propman.parse("${propertyPrefix}if")
        return checkCondition(conditionIfObj)
    }

    boolean checkCondition(def conditionIfObj){
        return propman.match(conditionIfObj)
    }



    void set(String propertyName, def value){
        propman.set("${propertyPrefix}${propertyName}", value ?: '')
    }

    void setToRawProperty(String propertyName, def value){
        propman.set(propertyName, value ?: '')
    }


    
    /*****
     *
     *****/
    @Filter('get')
    def get(String propertyName){
        return propman.get("${propertyPrefix}${propertyName}") ?: propman.get(propertyName)
    }

    @Filter('getRaw')
    def getRaw(String propertyName){
        return propman.getRaw("${propertyPrefix}${propertyName}") ?: propman.getRaw(propertyName)
    }

    @Filter('parse')
    def parse(String propertyName){
        return propman.parse("${propertyPrefix}${propertyName}") ?: propman.parse(propertyName)
    }

    @Filter('getString')
    String getString(String propertyName){
        return propman.getString("${propertyPrefix}${propertyName}") ?: propman.getString(propertyName)
    }

    @Filter('getInteger')
    Integer getInteger(String propertyName){
        Integer localValue = propman.getInteger("${propertyPrefix}${propertyName}")
        Integer globalValue = propman.getInteger(propertyName)
        return (localValue != null) ? localValue : (globalValue != null) ? globalValue : null
    }

    @Filter('getShort')
    Short getShort(String propertyName){
        Integer localValue = propman.getInteger("${propertyPrefix}${propertyName}")
        Integer globalValue = propman.getInteger(propertyName)
        return (localValue != null) ? localValue : (globalValue != null) ? globalValue : null
    }

    @Filter('getDouble')
    Double getDouble(String propertyName){
        Integer localValue = propman.getInteger("${propertyPrefix}${propertyName}")
        Integer globalValue = propman.getInteger(propertyName)
        return (localValue != null) ? localValue : (globalValue != null) ? globalValue : null
    }

    @Filter('getLong')
    Long getLong(String propertyName){
        Integer localValue = propman.getInteger("${propertyPrefix}${propertyName}")
        Integer globalValue = propman.getInteger(propertyName)
        return (localValue != null) ? localValue : (globalValue != null) ? globalValue : null
    }

    @Filter('getBoolean')
    Boolean getBoolean(String propertyName){
        Boolean localValue = propman.getBoolean("${propertyPrefix}${propertyName}")
        Boolean globalValue = propman.getBoolean(propertyName)
        return (localValue != null) ? localValue : (globalValue != null) ? globalValue : null
    }

    @Filter('getMap')
    Map getMap(String propertyName){
        Map map = parse(propertyName)
        return map
    }

    @Filter('getList')
    List getList(String propertyName){
        List resultList = []
        def value = parse(propertyName)
        if (value != null){
            if (value instanceof List){
                resultList = value
            }else{
                resultList << value
            }
        }
        return resultList
    }

    @Filter('getFilePathList')
    List<String> getFilePathList(String propertyName){
        return getFilePathList(propertyName, '')
    }

    List<String> getFilePathList(String propertyName, String extention){
        List<String> resultList = []
        List<String> valueList = getList(propertyName)
        valueList.each{
            resultList.addAll( FileMan.getSubFilePathList(it, extention) )
        }
        return resultList
    }

    @Filter('getFilePath')
    String getFilePath(String propertyName){
        String filePath = get(propertyName)
        return FileMan.getFullPath(filePath)
    }

}
