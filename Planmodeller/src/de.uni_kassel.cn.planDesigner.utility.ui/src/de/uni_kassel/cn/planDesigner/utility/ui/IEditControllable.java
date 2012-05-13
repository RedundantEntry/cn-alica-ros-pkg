package de.uni_kassel.cn.planDesigner.utility.ui;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.swt.widgets.Widget;

public interface IEditControllable
{
	public void updateView(Notification n);

	public void enterPressed(Widget source);

	public void focusOutEvent(Widget source);

	public void modifyEvent(Widget source);

	public void selectionEvent(Object source);
}
