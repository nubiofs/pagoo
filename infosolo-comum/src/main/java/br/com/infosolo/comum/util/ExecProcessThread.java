package br.com.infosolo.comum.util;

public class ExecProcessThread extends Thread {
	private Process process;
	private int timeOut;

	public ExecProcessThread(int timeOut, Process process) {
		this.process = process;
		this.timeOut = timeOut;
		this.start();
	}

	public void run() {
		try {
			// Aguarda o tempo limite
			sleep(timeOut * 1000);
			
			// Testa se o processo já finalizou
			process.exitValue();
			
		} catch (IllegalThreadStateException e) {
			process.destroy();
			
		} catch (InterruptedException e) {
			System.out.println("Interrupted Exception");
			return;
		}
	}
}
