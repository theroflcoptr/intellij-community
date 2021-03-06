/*
 * Copyright 2000-2016 JetBrains s.r.o.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jetbrains.idea.devkit.inspections;

import com.intellij.openapi.module.Module;
import org.jetbrains.annotations.Nls;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.idea.devkit.inspections.quickfix.CreateHtmlDescriptionFix;

/**
 * @author Konstantin Bulenkov
 */
public class IntentionDescriptionNotFoundInspection extends DescriptionNotFoundInspectionBase {

  public IntentionDescriptionNotFoundInspection() {
    super(DescriptionType.INTENTION);
  }

  protected CreateHtmlDescriptionFix getFix(Module module, String descriptionDir) {
    return new CreateHtmlDescriptionFix(descriptionDir, module, DescriptionType.INTENTION);
  }

  @NotNull
  protected String getHasNotDescriptionError() {
    return "Intention does not have a description";
  }

  @NotNull
  protected String getHasNotBeforeAfterError() {
    return "Intention must have 'before.*.template' and 'after.*.template' beside 'description.html'";
  }

  @NotNull
  public String getShortName() {
    return "IntentionDescriptionNotFoundInspection";
  }
}
