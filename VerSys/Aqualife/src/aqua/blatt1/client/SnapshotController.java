package aqua.blatt1.client;

import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JOptionPane;

public class SnapshotController implements ActionListener {
	private final Component parent;

	public SnapshotController(Component parent) {
		this.parent = parent;
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		System.out.println("lol");
	}
}
