package caseloader;
import py4j.GatewayServer;


public class ProblemCreatorEntryPoint {
	private ProblemCreator pc;
	
	public ProblemCreatorEntryPoint() {
		pc = new ProblemCreator();
	}
	
	public ProblemCreator getProblemCreator() {
		return pc;
	}
	
	public static void main(String[] args) {
        GatewayServer gatewayServer = new GatewayServer(new ProblemCreatorEntryPoint());
        gatewayServer.start();
        System.out.println("GW server started.");
	}
}
