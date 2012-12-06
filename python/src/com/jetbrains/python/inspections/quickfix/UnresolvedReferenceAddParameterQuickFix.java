package com.jetbrains.python.inspections.quickfix;

import com.intellij.codeInsight.CodeInsightUtilBase;
import com.intellij.codeInsight.intention.HighPriorityAction;
import com.intellij.codeInsight.template.TemplateBuilder;
import com.intellij.codeInsight.template.TemplateBuilderFactory;
import com.intellij.codeInspection.LocalQuickFix;
import com.intellij.codeInspection.ProblemDescriptor;
import com.intellij.openapi.project.Project;
import com.intellij.openapi.util.TextRange;
import com.intellij.psi.PsiElement;
import com.intellij.psi.util.PsiTreeUtil;
import com.jetbrains.python.PyBundle;
import com.jetbrains.python.psi.PyElementGenerator;
import com.jetbrains.python.psi.PyFunction;
import com.jetbrains.python.psi.PyNamedParameter;
import com.jetbrains.python.psi.PyParameterList;
import org.jetbrains.annotations.NotNull;

/**
 * User: ktisha
 *
 * QuickFix to add parameter to unresolved reference
 */
public class UnresolvedReferenceAddParameterQuickFix implements LocalQuickFix, HighPriorityAction {

  public UnresolvedReferenceAddParameterQuickFix() {
  }

  @NotNull
  public String getName() {
    return PyBundle.message("QFIX.unresolved.reference.add.param");
  }

  @NotNull
  public String getFamilyName() {
    return PyBundle.message("QFIX.unresolved.reference.add.param");
  }

  public void applyFix(@NotNull Project project, @NotNull ProblemDescriptor descriptor) {
    final PsiElement element = descriptor.getPsiElement();
    PyElementGenerator elementGenerator = PyElementGenerator.getInstance(project);
    PyNamedParameter parameter = elementGenerator.createParameter(element.getText() + "=None");
    final PyFunction function = PsiTreeUtil.getParentOfType(element, PyFunction.class);
    if (function != null) {
      final PyParameterList parameterList = function.getParameterList();
      parameterList.addParameter(parameter);
      CodeInsightUtilBase.forcePsiPostprocessAndRestoreElement(parameterList);
      final TemplateBuilder builder = TemplateBuilderFactory.getInstance().createTemplateBuilder(parameter);
      builder.replaceRange(TextRange.create(parameter.getTextLength() - 4, parameter.getTextLength()), "None");
      builder.run();
    }
  }
}
