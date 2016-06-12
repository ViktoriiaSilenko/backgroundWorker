package threadbackground;

import java.io.File;
import java.io.IOException;

import javax.swing.SwingWorker;

import org.apache.commons.io.FileUtils;

class Task extends SwingWorker<Void, Void> {
	private BasicCopy basicCopy;

	public Task(BasicCopy basicCopy) {
		super();
		this.basicCopy = basicCopy;
	}

	@Override
	public Void doInBackground() {
		setProgress(0);

		File src = new File(basicCopy.MAIN_DIR);

		File dest = new File(basicCopy.BACKUP_DIR);

		try {
			FileUtils.copyDirectory(src, dest);
			if (BasicCopy.getDirSize(src) >= basicCopy.ONE_PERCENT) {
				basicCopy.currentPercent++;
				basicCopy.getProgressBar().setValue(basicCopy.currentPercent);
				basicCopy.currentSize = 0;
			} else {
				basicCopy.currentSize = basicCopy.currentSize + BasicCopy.getDirSize(src);
				if (basicCopy.currentSize >= basicCopy.ONE_PERCENT) {
					basicCopy.currentPercent++;
					basicCopy.getProgressBar().setValue(basicCopy.currentPercent);
					basicCopy.currentSize = 0;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return null;
	}

	@Override
	public void done() {
		basicCopy.closeWindow();
	}
}