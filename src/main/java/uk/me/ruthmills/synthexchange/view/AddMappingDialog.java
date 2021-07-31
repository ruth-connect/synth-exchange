package uk.me.ruthmills.synthexchange.view;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep;
import com.vaadin.flow.component.formlayout.FormLayout.ResponsiveStep.LabelsPosition;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import uk.me.ruthmills.synthexchange.model.mapping.DeviceMapping;
import uk.me.ruthmills.synthexchange.model.mapping.DeviceToDevice;
import uk.me.ruthmills.synthexchange.model.mapping.Mapping;
import uk.me.ruthmills.synthexchange.service.DeviceMappingService;
import uk.me.ruthmills.synthexchange.service.MappingService;

@SpringComponent
@UIScope
public class AddMappingDialog extends Dialog {

	private static final long serialVersionUID = 1L;

	private DeviceMappingService deviceMappingService;

	private FormLayout formLayout;
	private Select<DeviceMapping> inputSelect;
	private Select<DeviceMapping> outputSelect;
	private Select<DeviceToDevice> mappingSelect;
	private Button addMappingButton;
	private Button cancelButton;

	@Autowired
	public AddMappingDialog(MappingService mappingService, DeviceMappingService deviceMappingService) {

		this.deviceMappingService = deviceMappingService;
		this.setWidth("400px");

		formLayout = new FormLayout();
		formLayout.setResponsiveSteps(new ResponsiveStep("0", 1, LabelsPosition.TOP));
		formLayout.add(new Text("Add Mapping"));
		add(formLayout);

		inputSelect = new Select<>();
		inputSelect.setLabel("Input");
		inputSelect.setPlaceholder("Select an input");
		inputSelect.setItemLabelGenerator(DeviceMapping::getName);
		inputSelect.setItems(deviceMappingService.getInputs());
		formLayout.add(inputSelect);

		outputSelect = new Select<>();
		outputSelect.setLabel("Output");
		outputSelect.setPlaceholder("Select an output");
		outputSelect.setItemLabelGenerator(DeviceMapping::getName);
		outputSelect.setItems(deviceMappingService.getOutputs());
		formLayout.add(outputSelect);

		mappingSelect = new Select<>();
		mappingSelect.setLabel("Mapping");
		mappingSelect.setPlaceholder("Select a mapping");
		mappingSelect.setItemLabelGenerator(DeviceToDevice::getName);
		mappingSelect.setItems(mappingService.getMappings());
		formLayout.add(mappingSelect);

		addMappingButton = new Button("Add Mapping", e -> addMapping());
		formLayout.add(addMappingButton);

		cancelButton = new Button("Cancel", e -> cancel());
		formLayout.add(cancelButton);
	}

	private void addMapping() {
		DeviceMapping input = inputSelect.getValue();
		DeviceMapping output = outputSelect.getValue();
		DeviceToDevice deviceToDevice = mappingSelect.getValue();
		if (input != null && output != null && deviceToDevice != null) {
			Mapping mapping = new Mapping();
			mapping.setInput(input);
			mapping.setOutput(output);
			mapping.setMapping(deviceToDevice);

			deviceMappingService.addMapping(mapping);
		}
		clear();
		this.close();
	}

	private void cancel() {
		clear();
		this.close();
	}

	private void clear() {
		inputSelect.setValue(null);
		outputSelect.setValue(null);
		mappingSelect.setValue(null);
	}
}
