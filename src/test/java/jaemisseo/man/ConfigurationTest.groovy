package jaemisseo.man

import jaemisseo.man.configuration.Config
import org.junit.Test

class ConfigurationTest {

    @Test
    void main(){

        /** Make Properties **/
        String[] args = []
        Config config = new Config().makeProperties(args)
        assert config
    }


    @Test
    void propertiesTest(){
        /** Make Properties **/
        String[] args = 'commandA commandB commandC -dashOptionA -dashOptionB -dashOptionC=dashOptionValue --dashDashOption1 --dashDashOption2'.split(' ')
        Config config = new Config().makeProperties(args)

        println config.propGen
        println config.propGen.programProperties
        println config.propGen.defaultProperties
        println config.propGen.externalProperties

        println config.propGen.getNoDashOptionList()
        println config.propGen.getDashOptionList()
        println config.propGen.getDashDashOptionList()
        println config.propGen.getDashOptions()

        /** NoDashOption **/
        assert config.propGen.hasNoDashOption('commandA')
        assert config.propGen.hasNoDashOption('commandB')
        assert config.propGen.hasNoDashOption('commandC')
        assert !config.propGen.hasNoDashOption('commandD')
        assert !config.propGen.hasNoDashOption('commandE')

        /** DashOption (KEY) **/
        assert config.propGen.hasDashOption('dashOptionA')
        assert config.propGen.hasDashOption('dashOptionB')
        assert config.propGen.hasDashOption('dashOptionC')
        assert !config.propGen.hasDashOption('dashOptionD')

        /** DashOption (KEY and VALUE) **/
        assert config.propGen.hasDashOption('dashOptionA', [])
//        assert config.propGen.hasDashOption('dashOptionA', true)
//        assert config.propGen.hasDashOption('dashOptionA', 'true')
//        assert !config.propGen.hasDashOption('dashOptionA', false)
//        assert !config.propGen.hasDashOption('dashOptionA', 'false')
//        assert !config.propGen.hasDashOption('dashOptionA', '')
        assert !config.propGen.hasDashOption('dashOptionB', 'false')
        assert !config.propGen.hasDashOption('dashOptionC', '1')
        assert !config.propGen.hasDashOption('dashOptionC', 'A')
        assert config.propGen.hasDashOption('dashOptionC', 'dashOptionValue')
        assert !config.propGen.hasDashOption('dashOptionD', 'testValue')

        /** DashOption (value) **/
        assert config.propGen.getValueFromDashOption('dashOptionA') == []
        assert config.propGen.getValueFromDashOption('dashOptionB') == []
        assert config.propGen.getValueFromDashOption('dashOptionC') == 'dashOptionValue'
        assert config.propGen.getValueFromDashOption('dashOptionD') == null

        /** DashDashOption **/
        assert config.propGen.hasDashDashOption('dashDashOption1')
        assert config.propGen.hasDashDashOption('dashDashOption2')
        assert !config.propGen.hasDashDashOption('dashDashOption3')
    }

}

