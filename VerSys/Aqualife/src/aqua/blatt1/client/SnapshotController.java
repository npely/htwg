package aqua.blatt1.client;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

public class SnapshotController implements ActionListener {
	TankModel tankModel;

	public SnapshotController(TankModel tankModel) {
		this.tankModel = tankModel;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		this.tankModel.initiateSnapshot();
	}
}
