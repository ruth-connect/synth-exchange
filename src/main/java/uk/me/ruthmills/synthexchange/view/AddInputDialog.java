package uk.me.ruthmills.synthexchange.view;

import java.util.stream.Collectors;
import java.util.stream.IntStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

import uk.me.ruthmills.synthexchange.model.device.Device;
import uk.me.ruthmills.synthexchange.model.device.MidiDevice;
import uk.me.ruthmills.synthexchange.model.mapping.DeviceMapping;
import uk.me.ruthmills.synthexchange.service.DeviceMappingService;
import uk.me.ruthmills.synthexchange.service.DeviceService;
import uk.me.ruthmills.synthexchange.service.MidiService;

@SpringComponent
@UIScope
public class AddInputDialog extends Dialog {

	private static final long serialVersionUID = 1L;

	private MidiService midiService;
	private DeviceMappingService deviceMappingService;

	private FormLayout formLayout;
	private Select<Device> deviceSelect;
	private Select<javax.sound.midi.MidiDevice.Info> midiInterfaceSelect;
	private Select<Integer> midiChannelSelect;
	private Button addInputButton;
	private Button cancelButton;

	private static final Logger logger = LoggerFactory.getLogger(AddInputDialog.class);

	@Autowired
	public AddInputDialog(MidiService midiService, DeviceService deviceService,
			DeviceMappingService deviceMappingService) {
		this.midiService = midiService;
		this.deviceMappingService = deviceMappingService;
		this.setWidth("400px");

		formLayout = new FormLayout();
		formLayout.setResponsiveSteps(new ResponsiveStep("0", 1, LabelsPosition.TOP));
		formLayout.add(new Text("Add Input"));
		add(formLayout);

		deviceSelect = new Select<>();
		deviceSelect.setLabel("Device");
		deviceSelect.setPlaceholder("Select a device");
		deviceSelect.setItemLabelGenerator(Device::getName);
		deviceSelect.setItems(deviceService.getDevices());
		formLayout.add(deviceSelect);

		midiInterfaceSelect = new Select<>();
		midiInterfaceSelect.setLabel("MIDI Interface");
		midiInterfaceSelect.setPlaceholder("Select a MIDI interface");
		midiInterfaceSelect.setItemLabelGenerator(javax.sound.midi.MidiDevice.Info::getName);
		midiInterfaceSelect.setItems(midiService.getMidiInputs());
		formLayout.add(midiInterfaceSelect);

		midiChannelSelect = new Select<>();
		midiChannelSelect.setLabel("MIDI Channel");
		midiChannelSelect.setPlaceholder("Select a MIDI channel");
		midiChannelSelect.setItems(IntStream.rangeClosed(1, 16).boxed().collect(Collectors.toList()));
		formLayout.add(midiChannelSelect);

		addInputButton = new Button("Add Input", e -> addInput());
		formLayout.add(addInputButton);

		cancelButton = new Button("Cancel", e -> cancel());
		formLayout.add(cancelButton);
	}

	private void addInput() {
		Device device = deviceSelect.getValue();
		if (device != null) {
			DeviceMapping deviceMapping = new DeviceMapping();
			deviceMapping.setManufacturer(device.getManufacturer());

			if (device instanceof MidiDevice) {
				MidiDevice midiDevice = (MidiDevice) device;
				deviceMapping.setModel(midiDevice.getModel());
				deviceMapping.setConnection(midiInterfaceSelect.getValue().getName());
				deviceMapping.setChannel(midiChannelSelect.getValue().toString());
				deviceMapping.setDevice(device);

				try {
					midiService.openMidiInput(midiInterfaceSelect.getValue());
				} catch (Exception ex) {
					logger.error("Failed to open MIDI input: " + midiInterfaceSelect.getValue().getName(), ex);
				}
			}

			deviceMappingService.addInput(deviceMapping);
		}
		clear();
		this.close();
	}

	private void cancel() {
		clear();
		this.close();
	}

	private void clear() {
		deviceSelect.setValue(null);
		midiInterfaceSelect.setValue(null);
		midiChannelSelect.setValue(null);
	}
}
