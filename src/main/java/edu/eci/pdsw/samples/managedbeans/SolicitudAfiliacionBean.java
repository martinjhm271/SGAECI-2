/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.pdsw.samples.managedbeans;

import edu.eci.pdsw.samples.entities.Egresado;
import edu.eci.pdsw.samples.entities.Egresado_Empresa;
import edu.eci.pdsw.samples.entities.Estudiante;
import edu.eci.pdsw.samples.entities.Rol;
import edu.eci.pdsw.samples.entities.SolicitudAfiliacion;
import edu.eci.pdsw.samples.services.ExcepcionServiciosSAGECI;
import edu.eci.pdsw.samples.services.ServiciosSAGECI;
import java.io.Serializable;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

/**
 *
 * @author pdswgr2
 */


@ManagedBean (name= "SolicitudAfiliacion")
@SessionScoped

public class SolicitudAfiliacionBean implements Serializable{
    ServiciosSAGECI SAGECI=ServiciosSAGECI.getInstance();
    private int solicitudID;
    private static int documentoID,Telefono,telefonoOficina,codigoEstudiante,semestrePonderado;
    private static BigInteger telefono2;
    private Date fechaGraduacion=new Date(new java.util.Date().getTime());
    private static String correo="correo",labora="no",semestreGrado="9999-9",tipoDocumentoID="CC",genero="MASCULINO",tipoSolicitante="EGRESADO",estadoSolicitud="NO REVISADO",comentario="Falta revision respectiva.",Nombre="Nombre",Apellido="Apellido",direccionVivienda="Direccion",Empresa="Empresa",direccionEmpresa="Direccion",Cargo="Cargo",correoPersonal="Correo",carrera="Carrera";
    private boolean acepta = false; 
    private ArrayList<Integer> semestres;
    private ArrayList<String> carreras;
    private boolean marca = false;
    private Egresado_Empresa egresadoEmpresa;
    private Rol rol;
    
    public SolicitudAfiliacionBean() {
        this.semestres  = new ArrayList<>();
        this.carreras = new ArrayList<>();
        egresadoEmpresa=new Egresado_Empresa();
        for (int i=8; i<=10; i++){
            this.semestres.add(i); 
        }
        carreras.add("INGENIERÍA CIVIL");carreras.add("INGENIERÍA INDUSTRIAL");carreras.add("INGENIERÍA MECÁNICA");carreras.add("INGENIERÍA ELECTRÓNICA");carreras.add("MATEMÁTICAS");
        carreras.add("INGENIERÍA AMBIENTAL"); carreras.add("INGENIERÍA ELÉCTRICA");carreras.add("ECONOMÍA");carreras.add("INGENIERÍA BIOMÉDICA");carreras.add("INGENIERÍA DE SISTEMAS"); carreras.add("ADMINISTRACIÓN DE EMPRESAS");

    }

    public void agregarSolicitudAfiliacion() throws ExcepcionServiciosSAGECI{
        if(marca){
            labora="si";
            egresadoEmpresa.setNombreempre(Empresa);
            egresadoEmpresa.setDirempre(direccionEmpresa);
            egresadoEmpresa.setTelempre(telefonoOficina);
        }else{
            labora="no";
            Cargo=null;
        }
        if(correo.equals("correo")) correo=null;
        if(!Nombre.equals("Nombre")){
            Egresado e1 = new Egresado(documentoID,  Telefono,  telefono2, tipoDocumentoID,  Nombre, Apellido, direccionVivienda,  correo,  genero,rol, semestreGrado,correoPersonal, Cargo,  labora,egresadoEmpresa,fechaGraduacion);
            Estudiante e2 = new Estudiante(documentoID,  Telefono,  telefono2, tipoDocumentoID,  Nombre, Apellido, direccionVivienda,  correo,  genero,rol, codigoEstudiante, semestrePonderado,  carrera);
            if(semestreGrado.equals("9999-9")){
                e1=null;
            }else{
                e2=null;
            }
            SolicitudAfiliacion temp = new SolicitudAfiliacion( solicitudID, new Date(new java.util.Date().getTime()) ,  estadoSolicitud,  comentario,  e1,  e2);
            SAGECI.registrarNuevaSolicitud(temp);
        }
        resetearValores();
    }
    
    public static void resetearValores(){
        telefono2 = new BigInteger("0");documentoID = 0;Telefono = 0;telefonoOficina = 0;codigoEstudiante = 0;semestrePonderado = 0;
        correo="correo";labora="no";semestreGrado="9999-9";tipoDocumentoID="CC";genero="MASCULINO";tipoSolicitante="EGRESADO";comentario="Falta revision respectiva.";Nombre="Nombre";Apellido="Apellido";direccionVivienda="Direccion";Empresa="Empresa";direccionEmpresa="Direccion";Cargo="Cargo";correoPersonal="Correo";carrera="Carrera";
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public boolean getMarca() {
        return marca;
    }

    public void setMarca(boolean marca) {
        this.marca = marca;
    }

    public String getApellido() {
        return Apellido;
    }

    public void setApellido(String Apellido) {
        this.Apellido = Apellido;
    }
    
    public ArrayList<String> getCarreras() {
        return carreras;
    }

    public void setCarreras(ArrayList<String> carreras) {
        this.carreras = carreras;
    }
    
    public String getSemestreGrado() {
        return semestreGrado;
    }

    public void setSemestreGrado(String semestreGrado) {   
   
        this.semestreGrado = semestreGrado;
    }
    
    public int getDocumentoID() {
        return documentoID;
    }

    public void setDocumentoID(int documentoID) {
        this.documentoID = documentoID;
    }

    public String getTipoSolicitante() {
        return tipoSolicitante;
    }

    public void setTipoSolicitante(String tipoSolicitante) {
        this.tipoSolicitante = tipoSolicitante;
    }

    public String getEstadoSolicitud() {
        return estadoSolicitud;
    }

    public void setEstadoSolicitud(String estadoSolicitud) {
        this.estadoSolicitud = estadoSolicitud;
    }

    public String getComentario() {
        return comentario;
    }

    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    public boolean isAcepta() {
        return acepta;
    }

    public void setAcepta(boolean acepta) {
        this.acepta = acepta;
    }

    public ServiciosSAGECI getSAGECI() {
        return SAGECI;
    }

    public void setSAGECI(ServiciosSAGECI SAGECI) {
        this.SAGECI = SAGECI;
    }

    public int getSolicitudID() {
        return solicitudID;
    }

    public void setSolicitudID(int solicitudID) {
        this.solicitudID = solicitudID;
    }

    public int getTelefono() {
        return Telefono;
    }

    public void setTelefono(int Telefono) {
        this.Telefono = Telefono;
    }

    public BigInteger getTelefono2() {
        return telefono2;
    }

    public void setTelefono2(BigInteger telefono2) {
        this.telefono2 = telefono2;
    }

    public int getTelefonoOficina() {
        return telefonoOficina;
    }

    public void setTelefonoOficina(int telefonoOficina) {
        this.telefonoOficina = telefonoOficina;
    }

    public int getCodigoEstudiante() {
        return codigoEstudiante;
    }

    public void setCodigoEstudiante(int codigoEstudiante) {
        this.codigoEstudiante = codigoEstudiante;
    }

    public int getSemestrePonderado() {
        return semestrePonderado;
    }

    public void setSemestrePonderado(int semestrePonderado) {
        this.semestrePonderado = semestrePonderado;
    }

    public Date getFechaGraduacion() {
        return fechaGraduacion;
    }

    public void setFechaGraduacion(Date fechaGraduacion) {
        this.fechaGraduacion = fechaGraduacion;
    }

    public String getTipoDocumentoID() {
        return tipoDocumentoID;
    }

    public void setTipoDocumentoID(String tipoDocumentoID) {
        this.tipoDocumentoID = tipoDocumentoID;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getEmpresa() {
        return Empresa;
    }

    public void setEmpresa(String Empresa) {
        this.Empresa = Empresa;
    }

    public String getDireccionEmpresa() {
        return direccionEmpresa;
    }

    public void setDireccionEmpresa(String direccionEmpresa) {
        this.direccionEmpresa = direccionEmpresa;
    }

    public String getCargo() {
        return Cargo;
    }

    public void setCargo(String Cargo) {
        this.Cargo = Cargo;
    }

    public String getCorreoPersonal() {
        return correoPersonal;
    }

    public void setCorreoPersonal(String correoPersonal) {
        this.correoPersonal = correoPersonal;
    }

    public String getCarrera() {
        return carrera;
    }

    public void setCarrera(String carrera) {
        this.carrera = carrera;
    }

    public  ArrayList<Integer> getSemestres() {
        ArrayList<Integer> temp=new ArrayList<Integer>();
        if(carrera.equals("ADMINISTRACIÓN DE EMPRESAS")||carrera.equals("MATEMÁTICAS")||carrera.equals("ECONOMÍA")){
            for (int j=7;j<10;j++){temp.add(j);}
            return temp;
        }
        else{return semestres;}
    }

    public void setSemestres(ArrayList<Integer> semestres) {
        this.semestres = semestres;
    }

    public String getDireccionVivienda() {
        return direccionVivienda;
    }

    public void setDireccionVivienda(String direccionVivienda) {
        this.direccionVivienda = direccionVivienda;
    }

    public String getLabora() {
        return labora;
    }

    public void setLabora(String labora) {
        this.labora = labora;
    }
    
    public void asigna(String labora){
        this.labora=labora;
    }
}
