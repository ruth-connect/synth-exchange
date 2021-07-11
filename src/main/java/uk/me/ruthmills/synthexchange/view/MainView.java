package uk.me.ruthmills.synthexchange.view;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route
public class MainView extends VerticalLayout {

	private static final long serialVersionUID = 1L;

	public MainView() {
		add(new Text("Synth Exchange"));
	}
}
