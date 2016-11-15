/*
 * Copyright (C) 2015 hcadavid
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package edu.eci.pdsw.samples.services;

import edu.eci.pdsw.samples.entities.Egresado;
import edu.eci.pdsw.samples.entities.Egresado_Empresa;
import edu.eci.pdsw.samples.entities.Estudiante;
import edu.eci.pdsw.samples.entities.Persona;
import edu.eci.pdsw.samples.entities.SolicitudAfiliacion;
import java.io.IOException;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;


/**
 *
 * @author hcadavid
 */

public abstract class ServiciosSAGECI {
    
    private static final ServiciosSAGECI instance=new ServiciosSAGECIDAOS();
    
    protected ServiciosSAGECI(){        

    }
    
    public static ServiciosSAGECI getInstance() throws RuntimeException{        
        return instance;
    }

    public abstract void agregarEstudiante(int codigoEstudiante, int documentoID, String semestrePonderado, int telefono1,int telefono2, String tipoDocumentoID, String nombre,String direccion,String carrera,String correo,String genero);
    
    public abstract void agregarEgresado(int documentoID, int telefono1, int telefono2,String tipoDocumentoID, String nombre, String direccion, String correo, String genero, String cargo, String semestreGrado, String correoPersonal, String labora,Egresado_Empresa egresadoEmpresa,Date fechaGraduacion);

    
    /**
     * Consulta todas las solicitudes de afiliaciones registradas en la base de datos
     * @return el conjunto de las solicitudes de afiliaciones
     * @throws ExcepcionServiciosSAGECI si se presento un error de persistencia
     */
    public abstract List<SolicitudAfiliacion> consultarSolicitudAfiliaciones() throws ExcepcionServiciosSAGECI;


    /**
     * Dado un identificador, consulta una solicitud de afiliacion
     * @param SolicitudID identificador de la solicitud de afiliacion
     * @return la solicitud de afiliacion
     * @throws ExcepcionServiciosSAGECI si el identificador no corresponde a una solicitud
     */
    public abstract SolicitudAfiliacion consultarSolicitudAfiliacion(int SolicitudID) throws ExcepcionServiciosSAGECI;
    

    /**
     * REGISTRA una nueva solicitud de afiliacion a la pagina, el identificador y la fecha  se auto generan
     * @param s la nueva solicitud de afiliacion
     * @throws ExcepcionServiciosSAGECI si se presento un error de persistencia
     */
    public abstract void registrarNuevaSolicitud(SolicitudAfiliacion s) throws ExcepcionServiciosSAGECI;
    

    /**
     * Agrega una respuesta de porque fue rechazada la solicitud de afiliacion
     * @param SolicitudID identificador de la la solicitud de afiliacion
     * @param c el comentario a ser agregado
     * @throws ExcepcionServiciosSAGECI si el identificador no corresponde a una solicitud
     */
    public abstract void agregarComentarioSolicitud(int SolicitudID,String c) throws ExcepcionServiciosSAGECI;
    
    
    /**
     * Consulta una Persona registrada o afiliada
     * @param DocumentoID el documento con el cual la persona se registro
     * @return La persona registrada
     * @throws ExcepcionServiciosSAGECI si no hay una persona asociados al documento
     */
    public abstract Estudiante consultarEstudiante(int DocumentoID) throws ExcepcionServiciosSAGECI;
    
    /**
     * Consulta una Persona registrada o afiliada
     * @param DocumentoID el documento con el cual la persona se registro
     * @return La persona registrada
     * @throws ExcepcionServiciosSAGECI si no hay una persona asociados al documento
     */
    public abstract Egresado consultarEgresado(int DocumentoID) throws ExcepcionServiciosSAGECI;
       
    public abstract  void actualizarSolicitudAfliliacion(SolicitudAfiliacion s) throws ExcepcionServiciosSAGECI;
    
}