package fr.pantheonsorbonne.ufr27.miage.exception;

public class NoZoneFound extends Throwable {
	public static class NoZoneFoundById extends RuntimeException {

		public NoZoneFoundById(long zoneId){
			super("No zone found for this ID : " + zoneId);
			printMessage("No zone found for this ID : " + zoneId);
		}

		public void printMessage(String message) {
			System.out.println(message);
		}

	}
}
