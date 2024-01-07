package fr.pantheonsorbonne.ufr27.miage.dao;

import fr.pantheonsorbonne.ufr27.miage.model.Zone;

import java.util.List;


public interface ZoneDAO {

	 Zone findById(int idZone);
	 List<Zone> findAllZones();

	}
