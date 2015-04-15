/*
 * Copyright 2009 Andy Lee.
 * 
 * Licensed under the Apache License, Version 2.0 (the "License"); you may not
 * use this file except in compliance with the License. You may obtain a copy of
 * the License at
 * 
 * http://www.apache.org/licenses/LICENSE-2.0
 * 
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations under
 * the License.
 */
package uk.co.optimisticpanda.gtest.dto;

import uk.co.optimisticpanda.gtest.dto.condition.AllConditionTests;
import uk.co.optimisticpanda.gtest.dto.defaultfill.AllDefaultFillTests;
import uk.co.optimisticpanda.gtest.dto.defaultfill.defaultgens.AllDefaultGensTests;
import uk.co.optimisticpanda.gtest.dto.defaultfill.enggen.AllDefaultEngGenTests;
import uk.co.optimisticpanda.gtest.dto.edit.AllEditTests;
import uk.co.optimisticpanda.gtest.dto.propertyaccess.ognl.AllOgnlPropertyAccessTests;
import uk.co.optimisticpanda.gtest.dto.propertyaccess.rflc.AllReflectionPropertyAccessTests;
import uk.co.optimisticpanda.gtest.dto.rule.AllRuleTests;
import uk.co.optimisticpanda.gtest.dto.rulebuilder.fluent.AllFluentBuilderTests;
import uk.co.optimisticpanda.gtest.dto.rulebuilder.impl.AllRuleBuilderImplTests;
import junit.framework.Test;
import junit.framework.TestSuite;


/**
 * @author Andy Lee
 *
 */
public class AllTests {

    /**
     * @return suite
     */
    public static Test suite() {
        TestSuite suite = new TestSuite("All Tests");
        //$JUnit-BEGIN$
        suite.addTestSuite(SimpleDataEditorTest.class);
        suite.addTestSuite(MappedClassDataEditorTest.class);
        suite.addTest(AllEditTests.suite());
        suite.addTest(AllRuleTests.suite());
        suite.addTest(AllConditionTests.suite());
        suite.addTest(AllOgnlPropertyAccessTests.suite());
        suite.addTest(AllReflectionPropertyAccessTests.suite());
        suite.addTest(AllFluentBuilderTests.suite());
        suite.addTest(AllRuleBuilderImplTests.suite());
        suite.addTest(AllDefaultFillTests.suite());
        suite.addTest(AllDefaultGensTests.suite());
        suite.addTest(AllDefaultEngGenTests.suite());
        
        suite.addTestSuite(TestUtilsContextTest.class);
        
        //EXAMPLE
        suite.addTestSuite(ExampleTest.class);
        //$JUnit-END$
        return suite;
    }

}
