package uk.me.ruthmills.synthexchange.view;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.Text;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.formlayout.FormLayout;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import uk.me.ruthmills.synthexchange.model.device.Device;
import uk.me.ruthmills.synthexchange.service.DeviceMappingService;
import uk.me.ruthmills.synthexchange.service.DeviceService;
import uk.me.ruthmills.synthexchange.service.MidiService;

@SpringComponent
@UIScope
public class AddInputDialog extends Dialog {

	private static final long serialVersionUID = 1L;
	
	private FormLayout formLayout;
	private Select<Device> deviceSelect;
	private Button cancelButton;
	
	@Autowired
	public AddInputDialog(MidiService midiService, DeviceService deviceService, DeviceMappingService deviceMappingService) {
		
		formLayout = new FormLayout();
		formLayout.add(new Text("Add Input"));
		add(formLayout);
		
		deviceSelect = new Select<>();
		deviceSelect.setLabel("Device");
		deviceSelect.setItemLabelGenerator(Device::getName);
		deviceSelect.setItems(deviceService.getDevices());
		formLayout.add(deviceSelect);
		
		cancelButton = new Button("Cancel", e -> cancel());
		formLayout.add(cancelButton);
	}
	
	private void cancel() {
		deviceSelect.setValue(null);
		this.close();
	}
}
