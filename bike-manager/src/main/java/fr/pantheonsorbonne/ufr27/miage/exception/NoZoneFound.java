package fr.pantheonsorbonne.ufr27.miage.exception;

public class NoZoneFound extends Throwable {
	public static class NoZoneFoundById extends RuntimeException {

		public NoZoneFoundById(long zoneId){
			super("Aucune zone trouvée pour cet identifiant : " + zoneId);
			printMessage("Aucune zone trouvée pour cet identifiant : " + zoneId);
		}

		public void printMessage(String message) {
			System.out.println(message);
		}

	}
}
