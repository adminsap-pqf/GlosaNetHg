package org.mx.persistencia.comun.builder;

import java.util.ArrayList;
import java.util.List;

import org.mx.modelo.comun.bo.DashBoard;

public class BuilderDashBoard {

	public static List<DashBoard> buiderOperacionPeriodo(List<Object[]> lstPO)
	{
		List<DashBoard> lstDash = new ArrayList<DashBoard>();
		for (Object[] dashBoard : lstPO) 
		{
			DashBoard dash = new DashBoard();
			if (dashBoard[0] != null && !dashBoard[0].equals("")){
				dash.setTotalI(Integer.parseInt(dashBoard[0].toString()));
			}else{
				dash.setTotalI(0);
			}
			dash.setTipo(dashBoard[1].toString());
			lstDash.add(dash);
		}
		
		return lstDash;
	}

	public static List<DashBoard> buiderOperacionPorAduana(List<Object[]> lstPO)
	{
		List<DashBoard> lstDash = new ArrayList<DashBoard>();
		for (Object[] dashBoard : lstPO) 
		{
			DashBoard dash = new DashBoard();
			if (dashBoard[0] != null && !dashBoard[0].equals("")){
				dash.setTotalI(Integer.parseInt(dashBoard[0].toString()));
			}else{
				dash.setTotalI(0);
			}
			if (dashBoard[1] != null && !dashBoard[1].equals("")){
				dash.setClave(Integer.parseInt(dashBoard[1].toString()));
			}else{
				dash.setClave(0);
			}
			dash.setTipo(dashBoard[2].toString());
			lstDash.add(dash);
		}
		
		return lstDash;
	}
	
	public static List<DashBoard> builderTipoCambio(List<Object[]> lstPO)
	{
		List<DashBoard> lstDash = new ArrayList<DashBoard>();
		for (Object[] dashBoard : lstPO) 
		{
			DashBoard dash = new DashBoard();
			dash.setTipo(dashBoard[0].toString());
			
			if(dashBoard[1] != null && !dashBoard[1].equals("")){
				dash.setTotalD(Double.parseDouble(dashBoard[1].toString()));
			}else{
				dash.setTotalD(0.0);
			}
			
			lstDash.add(dash);
		}
		
		return lstDash;
	}
	
	public static List<DashBoard> buiderOperacionPorMedioTransporteArribo(List<Object[]> lstPO)
	{
		List<DashBoard> lstDash = new ArrayList<DashBoard>();
		for (Object[] dashBoard : lstPO) 
		{
			DashBoard dash = new DashBoard();
			if (dashBoard[0] != null && !dashBoard[0].equals("")){
				dash.setTotalI(Integer.parseInt(dashBoard[0].toString()));
			}else{
				dash.setTotalI(0);
			}
			if (dashBoard[0] != null && !dashBoard[0].equals("")){
				dash.setClave(Integer.parseInt(dashBoard[1].toString()));
			}else{
				dash.setClave(0);
			}
			dash.setTipo(dashBoard[2].toString());
			lstDash.add(dash);
		}
		
		return lstDash;
	}
	
	public static List<DashBoard> buiderPesoBrutoPorMedioTransporteArribo(List<Object[]> lstPO)
	{
		List<DashBoard> lstDash = new ArrayList<DashBoard>();
		for (Object[] dashBoard : lstPO) 
		{
			DashBoard dash = new DashBoard();
			if (dashBoard[0] != null && !dashBoard[0].equals("")){
				dash.setTotalD(Double.parseDouble(dashBoard[0].toString()));
			}else{
				dash.setTotalD(0.0);
			}
			if (dashBoard[1] != null && !dashBoard[1].equals("")){
				dash.setClave(Integer.parseInt(dashBoard[1].toString()));
			}else{
				dash.setClave(0);
			}
			dash.setTipo(dashBoard[2].toString());
			lstDash.add(dash);
		}
		
		return lstDash;
	}
	
	public static List<DashBoard> buiderOperacionTipoPedimento(List<Object[]> lstPO)
	{
		List<DashBoard> lstDash = new ArrayList<DashBoard>();
		for (Object[] dashBoard : lstPO) 
		{
			DashBoard dash = new DashBoard();
			if (dashBoard[0] != null && !dashBoard[0].equals("")){
				dash.setTotalI(Integer.parseInt(dashBoard[0].toString()));
			}else{
				dash.setTotalI(0);
			}
			if (dashBoard[1] != null && !dashBoard[1].equals("")){
				dash.setClave(Integer.parseInt(dashBoard[1].toString()));
				
			}else{
				dash.setClave(0);
			}
			dash.setTipo(dashBoard[2].toString());
			lstDash.add(dash);
		}
		
		return lstDash;
	}
	
	public static List<DashBoard> buiderOperacionClavePedimento(List<Object[]> lstPO)
	{
		List<DashBoard> lstDash = new ArrayList<DashBoard>();
		for (Object[] dashBoard : lstPO) 
		{
			DashBoard dash = new DashBoard();
			if (dashBoard[0] != null && !dashBoard[0].equals("")){
				dash.setTotalI(Integer.parseInt(dashBoard[0].toString()));
			}else{
				dash.setTotalI(0);
			}
			dash.setClaveStr(dashBoard[1].toString());
			dash.setTipo(dashBoard[2].toString());
			lstDash.add(dash);
		}
		
		return lstDash;
	}
	
	public static List<DashBoard> builderOperacionGuia(List<Object[]> lstPO)
	{
		List<DashBoard> lstDash = new ArrayList<DashBoard>();
		for (Object[] dashBoard : lstPO) 
		{
			DashBoard dash = new DashBoard();
			if (dashBoard[0] != null && !dashBoard[0].equals("")){
				try{
					dash.setClave(Integer.parseInt(dashBoard[0].toString()));
				}catch(java.lang.NumberFormatException e){
					dash.setClaveStr(dashBoard[0].toString());
				}
				
			}else{
				dash.setClave(0);
			}
			if (dashBoard[1] != null && !dashBoard[1].equals("")){
				try{
					dash.setTotalI(Integer.parseInt(dashBoard[1].toString()));
				}catch(java.lang.NumberFormatException e){
					dash.setTotalD(Double.parseDouble(dashBoard[1].toString()));
				}
				
				
			}else{
				dash.setTotalI(0);
			}
			
			if(dashBoard.length>2){
				if(Integer.parseInt(dashBoard[2].toString())==1){
					dash.setTipo("Importacion");
				}else if(Integer.parseInt(dashBoard[2].toString())==2){
					dash.setTipo("Exportacion");
				}else{
					dash.setTipo(dashBoard[2].toString());
				}
				
			}
			
			lstDash.add(dash);
		}
		return lstDash;
	}

	public static List<DashBoard> buiderOperacionPorAduanaT502(List<Object[]> lstPO)
	{
		List<DashBoard> lstDash = new ArrayList<DashBoard>();
		for (Object[] dashBoard : lstPO) 
		{
			DashBoard dash = new DashBoard();
			if (dashBoard[0] != null && !dashBoard[0].equals("")){
				dash.setTotalI(Integer.parseInt(dashBoard[0].toString()));
			}else{
				dash.setTotalI(0);
			}
			if (dashBoard[1] != null && !dashBoard[1].equals("")){
				dash.setClave(Integer.parseInt(dashBoard[1].toString()));
			}else{
				dash.setClave(0);
			}
			
			lstDash.add(dash);
		}
		
		return lstDash;
	}
	
	public static List<DashBoard> buiderOperacionPorPaisT502(List<Object[]> lstPO)
	{
		List<DashBoard> lstDash = new ArrayList<DashBoard>();
		for (Object[] dashBoard : lstPO) 
		{
			DashBoard dash = new DashBoard();
			if (dashBoard[0] != null && !dashBoard[0].equals("")){
				dash.setTotalI(Integer.parseInt(dashBoard[0].toString()));
			}else{
				dash.setTotalI(0);
			}
			if (dashBoard[1] != null && !dashBoard[1].equals("")){
				dash.setClaveStr(dashBoard[1].toString());
			}else{
				dash.setClaveStr("");
			}
			
			lstDash.add(dash);
		}
		
		return lstDash;
	}
	
	public static List<DashBoard> buiderOperacionesPorFormaDePagoT557(List<Object[]> lstPO)
	{
		List<DashBoard> lstDash = new ArrayList<DashBoard>();
		for (Object[] dashBoard : lstPO) 
		{
			DashBoard dash = new DashBoard();
			if (dashBoard[0] != null && !dashBoard[0].equals("")){
				dash.setTotalI(Integer.parseInt(dashBoard[0].toString()));
			}else{
				dash.setTotalI(0);
			}
			
			dash.setTipo(dashBoard[1].toString());
			
			if (dashBoard[2] != null && !dashBoard[2].equals("")){
				dash.setClave(Integer.parseInt(dashBoard[2].toString()));
			}else{
				dash.setClave(0);
			}
			lstDash.add(dash);
			
		}
		
		return lstDash;
	}
	public static List<DashBoard> buiderImportePagoPorTipoContribucionT557(List<Object[]> lstPO)
	{
		List<DashBoard> lstDash = new ArrayList<DashBoard>();
		for (Object[] dashBoard : lstPO) 
		{
			DashBoard dash = new DashBoard();
			if(dashBoard[0] != null && !dashBoard[0].equals("")){
				dash.setTotalD(Double.parseDouble(dashBoard[0].toString()));
			}else{
				dash.setTotalD(0.0);
			}

			if (dashBoard[1] != null && !dashBoard[1].equals("")){
				dash.setClave(Integer.parseInt(dashBoard[1].toString()));
			}else{
				dash.setClave(0);
			}
			
			lstDash.add(dash);
			
		}
		
		return lstDash;
	}
	
	public static List<DashBoard> buiderOperacionPorAduanaT557(List<Object[]> lstPO)
	{
		List<DashBoard> lstDash = new ArrayList<DashBoard>();
		for (Object[] dashBoard : lstPO) 
		{
			DashBoard dash = new DashBoard();
			if (dashBoard[0] != null && !dashBoard[0].equals("")){
				dash.setTotalI(Integer.parseInt(dashBoard[0].toString()));
			}else{
				dash.setTotalI(0);
			}
			dash.setTipo(dashBoard[1].toString());
			
			lstDash.add(dash);
		}
		
		return lstDash;
	}
	
	public static List<DashBoard> builderOperacionesMedioTrasporteSalida(List<Object[]> lstPO){
		List<DashBoard> lstDash = new ArrayList<DashBoard>();
		for (Object[] dashBoard : lstPO) 
		{
			DashBoard dash = new DashBoard();
			if (dashBoard[0] != null && !dashBoard[0].equals("")){
				dash.setClave(Integer.parseInt(dashBoard[0].toString()));
			}else{
				dash.setClave(0);
			}
			if (dashBoard[1] != null && !dashBoard[1].equals("")){
				dash.setTotalI(Integer.parseInt(dashBoard[1].toString()));
			}else{
				dash.setTotalI(0);
			}
			lstDash.add(dash);
		}
		return lstDash;
	}
	
	public static List<DashBoard> builderOperacionesPorIncrementablesYDeducibles(List<Object[]> lstPO){
		List<DashBoard> lstDash = new ArrayList<DashBoard>();
		DashBoard dashFletes = new DashBoard();
		DashBoard dashSeguros = new DashBoard();
		DashBoard dashEmbalajes = new DashBoard();
		DashBoard dashIncrementables = new DashBoard();
		DashBoard dashDeducibles = new DashBoard();
		for(Object[] dashBoard : lstPO){
			if(dashBoard[0] != null && !dashBoard[0].equals("")){
				dashFletes.setTotalD(Double.parseDouble(dashBoard[0].toString()));
				dashFletes.setClave(1);
			}else{
				dashFletes.setTotalD(0.0);
			}
			if(dashBoard[1] != null && !dashBoard[1].equals("")){
				dashSeguros.setTotalD(Double.parseDouble(dashBoard[1].toString()));
				dashSeguros.setClave(2);
			}else{
				dashSeguros.setTotalD(0.0);
			}
			if(dashBoard[2] != null && !dashBoard[2].equals("")){
				dashEmbalajes.setTotalD(Double.parseDouble(dashBoard[2].toString()));
				dashEmbalajes.setClave(3);
			}else{
				dashEmbalajes.setTotalD(0.0);
			}
			if(dashBoard[3] != null && !dashBoard[3].equals("")){
				dashIncrementables.setTotalD(Double.parseDouble(dashBoard[3].toString()));
				dashIncrementables.setClave(4);
			}else{
				dashIncrementables.setTotalD(0.0);
			}
			if(dashBoard[4] != null && !dashBoard[4].equals("")){
				dashDeducibles.setTotalD(Double.parseDouble(dashBoard[4].toString()));
				dashDeducibles.setClave(5);
			}else{
				dashDeducibles.setTotalD(0.0);
			}
		}
		lstDash.add(dashFletes);
		lstDash.add(dashSeguros);
		lstDash.add(dashEmbalajes);
		lstDash.add(dashIncrementables);
		lstDash.add(dashDeducibles);
		return lstDash;
	}
}
