package it.prova.gestionesatelliti.model;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class SatelliteCustomValidator implements Validator {

	@Override
	public boolean supports(Class<?> clazz) {
		// TODO Auto-generated method stub
		return Satellite.class.equals(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		// TODO Auto-generated method stub
		Satellite satellite = (Satellite) target;

		if (satellite.getDataLancio() != null && satellite.getDataRientro() != null
				&& satellite.getDataLancio().after(satellite.getDataRientro())) {

			errors.rejectValue("dataLancio", null,
					"La data di lancio del satellite deve essere antecedente alla data di rientro dello stesso");

		}

		if (satellite.getDataRientro() != null && satellite.getDataLancio() == null) {

			errors.rejectValue("dataLancio", null, "Bisogna inserire la data di lancio");

		}

		if ((satellite.getStato() == StatoSatellite.FISSO || satellite.getStato() == StatoSatellite.IN_MOVIMENTO)
				&& satellite.getDataRientro() != null) {

			errors.rejectValue("stato", null, "La data di rientro del satellite deve essere rimossa");

		}

	}

	public boolean validateRemove(Object target) {

		Satellite satellite = (Satellite) target;

		if (satellite.getDataRientro() != null && satellite.getStato() == StatoSatellite.DISATTIVATO)
			return true;
		
		if(satellite.getDataLancio() == null && satellite.getDataRientro() == null && satellite.getStato() == null)
			return true;
		
		return false;

	}

}
