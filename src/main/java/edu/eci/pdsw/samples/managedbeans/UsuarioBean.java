package edu.eci.pdsw.samples.managedbeans;

import com.itextpdf.text.Document;
import com.itextpdf.text.Element;
import com.itextpdf.text.Image;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.PageSize;

import com.itextpdf.text.pdf.PdfWriter;
import edu.eci.pdsw.samples.Security.SHA1;
import edu.eci.pdsw.samples.entities.Egresado;
import edu.eci.pdsw.samples.entities.Estudiante;
import edu.eci.pdsw.samples.entities.PagoAfiliacion;
import edu.eci.pdsw.samples.entities.Persona;
import edu.eci.pdsw.samples.entities.estadoAfiliacion;
import edu.eci.pdsw.samples.services.ExcepcionServiciosSAGECI;
import edu.eci.pdsw.samples.services.ServiciosSAGECI;
import java.awt.Color;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.OutputStream;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.faces.FacesException;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;

import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import org.primefaces.context.RequestContext;
import org.primefaces.model.DefaultStreamedContent;
import org.primefaces.model.StreamedContent;

/**
 *
 * @author Ricardo
 * @author Cristian
 */
@ManagedBean(name = "Usuario")
@SessionScoped
public class UsuarioBean {

    ServiciosSAGECI SAGECI = ServiciosSAGECI.getInstance();
    private static final long serialVersionUID = 1L;
    private int telefono = 0, telefono2 = 0;
    private String direccionVivienda = "", correo = "", contraactual = "", contranueva1 = "", contranueva2 = "";
    private StreamedContent streamedContent;
    private boolean documento = false, b = true;
    Estudiante estudiante;
    Egresado egresado;
    Persona persona;
    private int documentoID;
    String plantillaEst, plantillaEgr, plantillaRG;
    estadoAfiliacion eaf;
    PagoAfiliacion paf;

    public void showMessage(boolean m, String tipo) {
        FacesMessage message;
        if (tipo.equals("c")) {
            if (m) {
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "El Certificado se genero sin ningun problema.");
            } else {
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Incorrecto", "El Certificado no se pudo generar hubo un error inesperado.");
            }
        } else {
            if (m) {
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Correcto", "Se actualizaron los datos correctamente");
            } else {
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Incorrecto", "Hubo un error al momento de actualziar los datos.");
            }
        }
        RequestContext.getCurrentInstance().showMessageInDialog(message);
    }

    public PagoAfiliacion getPaf() {
        return paf;
    }

    public void setPaf(PagoAfiliacion paf) {
        this.paf = paf;
    }

    public boolean isDocumento() {
        return documento;
    }

    public void setDocumento(boolean documento) {
        this.documento = documento;
    }

    public Persona getPersona() {
        return persona;
    }

    public void setPersona(Persona persona) {
        this.persona = persona;
    }

    public estadoAfiliacion getEaf() {
        return eaf;
    }

    public void setEaf(estadoAfiliacion eaf) {
        this.eaf = eaf;
    }

    @PostConstruct
    public void init() {
        try {
            //----------------------------------
            Document doc = new Document();
            Rectangle rect = new Rectangle(PageSize.A4);
            doc.setPageSize(rect);
            OutputStream out = new ByteArrayOutputStream();
            PdfWriter writer = PdfWriter.getInstance(doc, out);
            doc.open();
            LogginBean bean = (LogginBean) getManagedBean("Loggin");
            documentoID = Integer.parseInt(bean.getUsername());
            /**
             * AGREGA LA IMAGEN DE LA ASOCIACION Paragraph image; image=new
             * Paragraph("LOGO ASOCIACION "); Image
             * image1=Image.getInstance("aecimagen.png");
             * image1.setAlignment(70); image1.setIndentationLeft(70);
             * image1.setSpacingAfter(30); image1.setSpacingBefore(20);
             * doc.add(image1);
             *
             *
             */

            Paragraph titulo;
            titulo = new Paragraph("CERTIFICADO DE AFILIACION AECI");
            titulo.setIndentationLeft(150);
            titulo.setAlignment(70);
            titulo.setSpacingAfter(70);
            titulo.setSpacingBefore(150);
            doc.add(titulo);

            doc.addCreationDate();
            Paragraph frase;
            frase = new Paragraph("La Asociación de Egresados de la Escuela Colombiana de Ingeniería Julio Garavito AECI,con Nit. 830.031.137-4, certifica :");
            frase.setAlignment(70);
            frase.setSpacingAfter(20);
            doc.add(frase);

            if (bean.getTipo().equals("Estudiante")) {
                estudiante = SAGECI.consultarEstudiante(documentoID);
                setPersona(estudiante);
            } else {
                egresado = SAGECI.consultarEgresado(documentoID);
                setPersona(egresado);
            }

            plantillaEst = "Que el Estudiante $1, identificado con $2  No. $3, cursando actualmente $5 semestre del programa de $4, está afiliado en la asociación de Estudiantes de la Escuela Colombiana de Ingeniería Julio Garavito desde $6 hasta $7, que cuenta con una afiliación gratuita de 6 meses dada su condición de estudiante activo.\n Es de anotar que para disfrutar de los convenios a los cuales  tiene derecho es necesario que su afiliación permanezca vigente realizando  el correspondiente pago anual. El presente certificado se expide con destino a los convenios de asociados a la  AECI en Bogotá.";
            plantillaEgr = "Que el Egresado $1, identificado con $2  No. $3, Egresado del periodo $4,  se encuentra afiliado en la asociación de egresados de la Escuela Colombiana de Ingeniería Julio Garavito desde $5 hasta $6 .La presente constancia se expide a solicitud del interesado.";
            setEaf(SAGECI.consultarEstadoAfiliacion(documentoID));
            if (bean.getTipo().equals("Estudiante")) {
                plantillaEst = plantillaEst.replace("$1", estudiante.getApellido() + " " + estudiante.getNombre());
                plantillaEst = plantillaEst.replace("$2", estudiante.getTipoDocumentoID());
                plantillaEst = plantillaEst.replace("$3", Integer.toString(documentoID));
                plantillaEst = plantillaEst.replace("$4", estudiante.getCarrera());
                plantillaEst = plantillaEst.replace("$5", Integer.toString(estudiante.getSemestrePonderado()));
                DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                Date fecha = eaf.getFechaInicio();
                plantillaEst = plantillaEst.replace("$6", df.format(fecha));
                Date fechafin = eaf.getFechaFin();
                plantillaEst = plantillaEst.replace("$7", df.format(fechafin));
                doc.addCreationDate();
                //plantillaEst=plantillaEst.replace("$8",doc.addCreationDate());

                Paragraph plantilla;
                plantilla = new Paragraph(plantillaEst);
                plantilla.setSpacingAfter(80);
                plantilla.setAlignment(70);
                doc.add(plantilla);

                
               

            } else {
                plantillaEgr = plantillaEgr.replace("$1", egresado.getApellido() + " " + egresado.getNombre());
                plantillaEgr = plantillaEgr.replace("$2", egresado.getTipoDocumentoID());
                plantillaEgr = plantillaEgr.replace("$3", Integer.toString(documentoID));
                plantillaEgr = plantillaEgr.replace("$4", egresado.getSemestreGrado());
                DateFormat df = new SimpleDateFormat("MM/dd/yyyy");
                Date fecha = eaf.getFechaInicio();
                plantillaEgr = plantillaEgr.replace("$5", df.format(fecha));
                Date fechafin = eaf.getFechaFin();
                plantillaEgr = plantillaEgr.replace("$6", df.format(fechafin));
                doc.add(new Paragraph(plantillaEgr));

            }
            Paragraph fin;
            fin = new Paragraph("Cordialmente\n\n JUAN CARLOS ROMERO ORDÓNEZ\n Director\n\n Asociación de Egresados Escuela Colombiana de Ingenieria Julio Garavito");
            fin.setAlignment(70);
            titulo.setSpacingAfter(150);
            titulo.setSpacingBefore(80);
            doc.add(fin);
            
            Paragraph info;
            info=new Paragraph("AK 45 No. 205 Bloque A - Piso 2 * Télefonos 6683600 ext 232 - Móvil 3124570612 * Correo Electrónico eaci@escuelaing.edu.co * Bogotá, Colombia");
            info.setSpacingBefore(150);
            
            doc.add(info);
            
            doc.close();
            out.close();

            InputStream in = new ByteArrayInputStream(((ByteArrayOutputStream) out).toByteArray());

            streamedContent = new DefaultStreamedContent(in, "application/pdf", "Certificado.pdf");
            //-------
            Map<String, Object> session = FacesContext.getCurrentInstance().getExternalContext().getSessionMap();
            byte[] b = (byte[]) session.get("reportBytes");
            if (b != null) {
                streamedContent = new DefaultStreamedContent(new ByteArrayInputStream(b), "application/pdf", "Certificado.pdf");
            }
        } catch (Exception e) {
            showMessage(false, "c");
            b = false;
        }
        if (b) {
            showMessage(true, "c");
        }
        b = true;
    }

    //==================================================================
    public StreamedContent getStreamedContent() {
        if (FacesContext.getCurrentInstance().getRenderResponse()) {
            return new DefaultStreamedContent();
        } else {
            return streamedContent;
        }
    }

    //==================================================================
    public void setStreamedContent(StreamedContent streamedContent) {
        this.streamedContent = streamedContent;
    }
    //=====================================================================

    public static Object getManagedBean(final String beanName) {
        FacesContext fc = FacesContext.getCurrentInstance();

        Object bean;
        try {
            ELContext elContext = fc.getELContext();
            bean = elContext.getELResolver().getValue(elContext, null, beanName);
        } catch (RuntimeException e) {
            throw new FacesException(e.getMessage(), e);

        }
        return bean;
    }

    public void UpdateInfo() {
        if (!direccionVivienda.equals("") && !correo.equals("") && telefono != 0 && telefono2 != 0) {
            try {
                LogginBean bean = (LogginBean) getManagedBean("Loggin");
                documentoID = Integer.parseInt(bean.getUsername());
                SAGECI.actualizarUsuario(documentoID, direccionVivienda, correo, telefono, telefono2);
            } catch (ExcepcionServiciosSAGECI ex) {
                Logger.getLogger(UsuarioBean.class.getName()).log(Level.SEVERE, null, ex);
                showMessage(false, "u");
                b = false;
            }
            if (b) {
                showMessage(true, "u");
            }
        }
        b = true;
    }

    public void UpdateContra() {
        if (!contraactual.equals("") && !contranueva1.equals("") && !contranueva2.equals("")) {
            try {
                LogginBean bean = (LogginBean) getManagedBean("Loggin");
                documentoID = Integer.parseInt(bean.getUsername());
                String contra = bean.getPassword();
                if (contraactual.equals(contra) && contranueva1.equals(contranueva2)) {
                    String nueva;
                    nueva = SHA1.generateHash(contranueva1);
                    SAGECI.actualizarContra(documentoID, contra);
                }
            } catch (ExcepcionServiciosSAGECI ex) {
                Logger.getLogger(UsuarioBean.class.getName()).log(Level.SEVERE, null, ex);
                showMessage(false, "u");
                b = false;
            }
            if (b) {
                showMessage(true, "u");
            }
        }
        b = true;
    }

    public int getTelefono() {
        return telefono;
    }

    public void setTelefono(int telefono) {
        this.telefono = telefono;
    }

    public int getTelefono2() {
        return telefono2;
    }

    public void setTelefono2(int telefono2) {
        this.telefono2 = telefono2;
    }

    public String getDireccionVivienda() {
        return direccionVivienda;
    }

    public void setDireccionVivienda(String direccionVivienda) {
        this.direccionVivienda = direccionVivienda;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContraactual() {
        return contraactual;
    }

    public void setContraactual(String contraactual) {
        this.contraactual = contraactual;
    }

    public String getContranueva1() {
        return contranueva1;
    }

    public void setContranueva1(String contranueva1) {
        this.contranueva1 = contranueva1;
    }

    public String getContranueva2() {
        return contranueva2;
    }

    public void setContranueva2(String contranueva2) {
        this.contranueva2 = contranueva2;
    }

}
