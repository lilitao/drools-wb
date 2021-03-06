/*
 * Copyright 2017 Red Hat, Inc. and/or its affiliates.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.drools.workbench.screens.guided.rule.client.widget;

import java.util.Collections;
import java.util.stream.Stream;

import com.google.gwt.user.client.ui.FlexTable;
import com.google.gwtmockito.GwtMockitoTestRunner;
import com.google.gwtmockito.WithClassesToStub;
import org.drools.workbench.models.datamodel.rule.FieldConstraint;
import org.drools.workbench.screens.guided.rule.client.OperatorsBaseTest;
import org.drools.workbench.screens.guided.rule.client.editor.CEPOperatorsDropdown;
import org.drools.workbench.screens.guided.rule.client.resources.images.GuidedRuleEditorImages508;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.kie.soup.project.datamodel.oracle.OperatorsOracle;
import org.kie.workbench.common.widgets.client.datamodel.OracleUtils;

import static org.mockito.Mockito.doReturn;
import static org.mockito.Mockito.spy;
import static org.mockito.Mockito.verify;

@WithClassesToStub({FlexTable.class, GuidedRuleEditorImages508.class, CEPOperatorsDropdown.class})
@RunWith(GwtMockitoTestRunner.class)
public class FactPatternWidgetTest extends OperatorsBaseTest {

    private FactPatternWidget factPatternWidget;

    @Before
    public void setUp() throws Exception {
        super.setUp();

        doReturn(Stream.of(singleFieldConstraint).toArray(FieldConstraint[]::new)).when(pattern).getFieldConstraints();

        factPatternWidget = spy(new FactPatternWidget(modeller,
                                                      eventBus,
                                                      pattern,
                                                      true, /* can bind*/
                                                      false)); /* not read only*/

        doReturn(connectives).when(factPatternWidget).getConnectives();
    }

    @Test
    public void testOperatorCompletionsString() throws Exception {
        doReturn("org.Address").when(singleFieldConstraint).getFactType();
        doReturn("street").when(singleFieldConstraint).getFieldName();

        factPatternWidget.drawConstraints(Collections.singletonList(singleFieldConstraint),
                                          pattern);

        verify(factPatternWidget).getNewOperatorDropdown(OracleUtils.joinArrays(OperatorsOracle.STRING_OPERATORS,
                                                                                OperatorsOracle.EXPLICIT_LIST_OPERATORS),
                                                         singleFieldConstraint);
    }

    @Test
    public void testOperatorCompletionsInteger() throws Exception {
        doReturn("org.Address").when(singleFieldConstraint).getFactType();
        doReturn("number").when(singleFieldConstraint).getFieldName();

        factPatternWidget.drawConstraints(Collections.singletonList(singleFieldConstraint),
                                          pattern);

        verify(factPatternWidget).getNewOperatorDropdown(OracleUtils.joinArrays(OperatorsOracle.COMPARABLE_OPERATORS,
                                                                                OperatorsOracle.EXPLICIT_LIST_OPERATORS),
                                                         singleFieldConstraint);
    }
}
