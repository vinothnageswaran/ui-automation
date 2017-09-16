/*
 * Copyright 2016-17 inpwtepydjuf@gmail.com
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
package mmarquee.automation.pattern;

import com.sun.jna.platform.win32.COM.Unknown;
import com.sun.jna.platform.win32.Guid;
import com.sun.jna.platform.win32.WinNT;
import com.sun.jna.ptr.PointerByReference;
import mmarquee.automation.AutomationException;
import mmarquee.automation.uiautomation.IUIAutomationInvokePattern;
import mmarquee.automation.uiautomation.IUIAutomationRangeValuePattern;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.mockito.invocation.InvocationOnMock;
import org.mockito.junit.MockitoJUnitRunner;
import org.mockito.stubbing.Answer;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.doReturn;

/**
 * Created by Mark Humphreys on 12/01/2017.
 */
@RunWith(MockitoJUnitRunner.class)
public class InvokePatternTest {
    @Mock
    IUIAutomationInvokePattern rawPattern;

    @Spy
    private Unknown mockUnknown;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void test_Invoke_Calls_getCurrentIsTopmost_From_Pattern() throws Exception {
        Invoke pattern = new Invoke(rawPattern);

        pattern.invoke();

        verify(rawPattern, atLeastOnce()).invoke();
    }

    @Test(expected= AutomationException.class)
    public void test_Invoke_Throws_Exception_When_Pattern_Returns_Error() throws Exception {
        doAnswer(new Answer() {
            @Override
            public Integer answer(InvocationOnMock invocation) throws Throwable {

                return 1;
            }
        }).when(rawPattern).invoke();

        Invoke pattern = new Invoke(rawPattern);

        pattern.invoke();
    }

    @Test(expected=AutomationException.class)
    @Ignore("Fails after mockito upgrade")
    public void test_That_getPattern_Throws_Exception_When_Pattern_Returns_Error() throws Exception {

        doAnswer(new Answer() {
            @Override
            public WinNT.HRESULT answer(InvocationOnMock invocation) throws Throwable {
                return new WinNT.HRESULT(-1);
            }
        }).when(mockUnknown).QueryInterface(any(Guid.REFIID.class), any(PointerByReference.class));

        Invoke pattern = new Invoke();

        Invoke spyPattern = Mockito.spy(new Invoke());

        IUIAutomationInvokePattern mockRange = Mockito.mock(IUIAutomationInvokePattern.class);

        doReturn(mockUnknown)
                .when(spyPattern)
                .makeUnknown(any());

        doReturn(mockRange)
                .when(spyPattern)
                .convertPointerToInterface(any(PointerByReference.class));

        spyPattern.invoke();

        verify(mockRange, atLeastOnce()).invoke();
    }

    @Test
    public void test_That_getPattern_Gets_Pattern_When_No_Pattern_Set() throws Exception {

        doAnswer(new Answer() {
            @Override
            public WinNT.HRESULT answer(InvocationOnMock invocation) throws Throwable {
                return new WinNT.HRESULT(1);
            }
        }).when(mockUnknown).QueryInterface(any(Guid.REFIID.class), any(PointerByReference.class));

        Invoke pattern = new Invoke();

        Invoke spyPattern = Mockito.spy(new Invoke());

        IUIAutomationInvokePattern mockRange = Mockito.mock(IUIAutomationInvokePattern.class);

        doReturn(mockUnknown)
                .when(spyPattern)
                .makeUnknown(any());

        doReturn(mockRange)
                .when(spyPattern)
                .convertPointerToInterface(any());

        spyPattern.invoke();

        verify(mockRange, atLeastOnce()).invoke();
    }
}