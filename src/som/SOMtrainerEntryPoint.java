package som;
import caseloader.ProblemCreator;
import py4j.GatewayServer;


public class SOMtrainerEntryPoint {
	private SOMtrainer som;
	private ProblemCreator pc;
	
	public SOMtrainerEntryPoint() {
	}
	
	
	public SOMtrainer getSOMtrainer() {
		return new SOMtrainer();
	}
	
	public static void main(String[] args) {
        GatewayServer gatewayServer = new GatewayServer(new SOMtrainerEntryPoint());
        gatewayServer.start();
        System.out.println("GW server started.");
	}
}
