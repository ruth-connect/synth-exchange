package uk.me.ruthmills.synthexchange.view;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;

import com.vaadin.flow.component.dialog.Dialog;
import com.vaadin.flow.component.select.Select;
import com.vaadin.flow.spring.annotation.SpringComponent;
import com.vaadin.flow.spring.annotation.UIScope;

import uk.me.ruthmills.synthexchange.model.device.Device;
import uk.me.ruthmills.synthexchange.service.DeviceService;
import uk.me.ruthmills.synthexchange.service.MidiService;

@SpringComponent
@UIScope
public class AddInputDialog extends Dialog {

	private static final long serialVersionUID = 1L;
	
	private Select<Device> deviceSelect;
	
	@Autowired
	public AddInputDialog(MidiService midiService, DeviceService deviceService) {
		deviceSelect = new Select<>();
		deviceSelect.setLabel("Device");
		deviceSelect.setItemLabelGenerator(Device::getName);
		deviceSelect.setItems(deviceService.getDevices());
		add(deviceSelect);
	}
}
