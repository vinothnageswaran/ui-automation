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
package mmarquee.automation.controls;

import com.sun.jna.ptr.PointerByReference;
import mmarquee.automation.AutomationElement;
import mmarquee.automation.AutomationException;
import mmarquee.automation.ControlType;
import mmarquee.automation.ElementNotFoundException;
import mmarquee.automation.controls.menu.AutomationMainMenu;
import mmarquee.automation.pattern.ItemContainer;
import mmarquee.automation.pattern.PatternNotFoundException;
import mmarquee.automation.uiautomation.TreeScope;

/**
 * @author Mark Humphreys
 * Date 04/03/2016.
 *
 * Wrapper for the TitleBar element.
 */
public class AutomationTitleBar extends AutomationContainer {
    /**
     * Constructor for the AutomationTitleBar.
     * @param element The underlying automation element
     * @throws AutomationException Automation library error
     * @throws PatternNotFoundException Failed to find pattern
     */
    public AutomationTitleBar(final AutomationElement element)
            throws PatternNotFoundException, AutomationException {
        super(element);
    }

    /**
     * Constructor for the AutomationTitleBar.
     * @param element The underlying automation element
     * @param container The underlying item container pattern
     * @throws AutomationException Automation library error
     * @throws PatternNotFoundException Failed to find pattern
     */
    public AutomationTitleBar(final AutomationElement element,
                              final ItemContainer container)
            throws PatternNotFoundException, AutomationException {
        super(element, container);
    }

    /**
     * Gets the menu bar for this title-bar.
     * @return The Main menu
     * @throws ElementNotFoundException When the element is not found
     */
    public AutomationMainMenu getMenuBar() throws AutomationException {
        PointerByReference condition = this.automation.createControlTypeCondition(ControlType.MenuBar);

        AutomationElement element = this.element.findFirst(new TreeScope(TreeScope.Descendants),
                condition);

        return new AutomationMainMenu(this.element, element);
    }
}
