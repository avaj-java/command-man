package jaemisseo.man

import jaemisseo.man.configuration.Config
import org.junit.Test

class groovy {

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
        String[] args = 'commandA commandB commandC -dashOptionA -dashOptionB -dashOptionCKey=dashOptionValue --dashDashOption1 --dashDashOption2'.split(' ')
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
        assert config.propGen.hasDashOption('dashOptionCKey')
        assert !config.propGen.hasDashOption('dashOptionD')

        /** DashOption (KEY and VALUE) **/
        assert config.propGen.hasDashOption('dashOptionA', true)
        assert config.propGen.hasDashOption('dashOptionA', 'true')
        assert !config.propGen.hasDashOption('dashOptionA', false)
        assert !config.propGen.hasDashOption('dashOptionA', 'false')
        assert !config.propGen.hasDashOption('dashOptionA', '')
        assert !config.propGen.hasDashOption('dashOptionB', 'false')
        assert !config.propGen.hasDashOption('dashOptionCKey', '1')
        assert !config.propGen.hasDashOption('dashOptionCKey', 'A')
        assert config.propGen.hasDashOption('dashOptionCKey', 'dashOptionValue')
        assert !config.propGen.hasDashOption('dashOptionD', 'testValue')

        /** DashDashOption **/
        assert config.propGen.hasDashDashOption('dashDashOption1')
        assert config.propGen.hasDashDashOption('dashDashOption2')
        assert !config.propGen.hasDashDashOption('dashDashOption3')
    }

}

