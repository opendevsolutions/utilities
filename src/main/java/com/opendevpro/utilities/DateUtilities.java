package com.opendevpro.utilities;
import java.io.IOException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Formatter;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/* 
 * utils.Date, sql.Date, Calendar NO permiten hacer esta logica en NINGUN CASO.
 *
 * Tener en cuenta que LocalDate es nueva por ende algunas cosas de Spring no es soportado y la mayoria de los engines
 * no lo reconoce como fecha porque es un Objeto mas complejo que Date, en ese caso seguir usando Date para persistencias.
 *
 * Para validaciones temporales usar LocalDate / LocalDateTime / Instant => jodatime
 * */

public class DateUtilities {

	public static final String DD_MM_YYYY_SLASH = "dd/MM/yyyy";
    public static final String YYYY_MM_DD_SLASH = "yyyy/MM/dd";
    public static final String DD_MM_YYYY_DASH = "dd-MM-yyyy";
    public static final String YYYY_MM_DD_DASH = "dd-MM-yyyy";
    public static final String DD_MMM_YYYY_DASH = "dd-MMM-yyyy";
    public static final String DD_MMM_YYYY_SLASH = "dd/MMM/yyyy";
    public static final String ISO_DATE_TIME_FORMAT_MILLI_SECONDS = "yyyy-MM-dd'T'HH:mm:ss.SSSz";
    public static final String ISO_DATE_TIME_FORMAT_MICRO_SECONDS = "yyyy-MM-dd'T'HH:mm:ss.SSSXXX";
    public static final String ISO_DATE_TIME_FORMAT_MINUTES = "yyyy-MM-dd'T'HH:mmz";
    public static final String ISO_DATE_TIME_ZONE_FORMAT_MINUTES = "yyyy-MM-dd'T'HH:mm'Z'";
    
    /**
     * Metodo que maneja una Exception si la fecha pasada como parametro existe o no en el calendario real.<br>
     * <p>
     * MUY PRECISO, diferencia 28/29 febrero de diferentes años.<br>
     * <p>
     * Ejemplo 20/12/2020 no falla, sin embargo 50/12/2020 si ya que esa fecha no existe por calendario.<br> 
     * <p>
     * Este metodo no es necesario colocarlo antes de la logica ya que solo devuelve un booleano y sirve para validar.
     * <p>
     * Usar los siguientes patterns que pertenecen a esta Clase y fueron probados:<br>
     * <p>
     * Super util cuando en swagger/ postman u otro programa ponen cualquier fecha inexistente, dias, meses.<br>
     * <p>
     * DD_MM_YYYY_SLASH<br>
     * YYYY_MM_DD_SLASH<br>
     * DD_MM_YYYY_DASH<br>
     * YYYY_MM_DD_DASH<br>
     * <p> 
     * @exception java.time.format.DateTimeParseException => Dispara la excepcion pero es manejada como boolean.
     * @author Axel A. Berlot
     * @return boolean.
     * @since 01-12-2020
     * @version 1.0
     */
    public static boolean isValidDate(String date, String pattern) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            LocalDate d = date != null ? LocalDate.parse(date, formatter) : null;
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * Es un metodo que tira una Exception si la fecha pasada como parametro no existe en el calendario.<br> 
     * <p>
     * MUY PRECISO, diferencia 28/29 febrero de diferentes años. 
     * <p>
     * Ejemplo => 20/12/2020 no falla, sin embargo 50/12/2020 si ya que esa fecha no existe por calendario.<br> 
     * <p>
     * Este metodo tiene que estar situado antes de la logica del metodo en si.
     * <p>
     * Se usa como pared ya que su funcion es TIRAR UNA EXCEPCION o no hacer nada<br>
     * <p>
     * Super util cuando en swagger/ postman u otro programa ponen cualquier fecha inexistente, dias, meses.<br>
     * <p>
     * Usar los siguientes patterns que pertenecen a esta Clase y fueron probados:<br>
     * <p>
     * DD_MM_YYYY_SLASH<br>
     * YYYY_MM_DD_SLASH<br>
     * DD_MM_YYYY_DASH<br>
     * YYYY_MM_DD_DASH<br>
     * <p>
     * @exception java.time.format.DateTimeParseException => Dispara la excepcion si no cumple con una fecha real por calendario
     * @author Axel A. Berlot
     * @return void.
     * @since 01-12-2020
     * @version 1.0
     */
    public static void firewallIsRealDate (String date, String pattern, boolean allowNulls) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        LocalDate pivotDate = null;
        if (allowNulls == true)
            pivotDate = date != null ? LocalDate.parse(date, formatter) : null;
        else
            pivotDate = LocalDate.parse(date, formatter);
    }
    

    /**
     * Es un metodo que tira una Exception si la fecha pasada como parametro no existe en el calendario.<br> 
     * <p>
     * MUY PRECISO, diferencia 28/29 febrero de diferentes años. 
     * <p>
     * Ejemplo => 20/12/2020 no falla, sin embargo 50/12/2020 si ya que esa fecha no existe por calendario.<br> 
     * <p>
     * Este metodo tiene que estar situado antes de la logica del metodo en si.
     * <p>
     * Se usa como pared ya que su funcion es TIRAR UNA EXCEPCION o no hacer nada<br>
     * <p>
     * SE AGREGA PARAMETRO LOCALE
     * <p>
     * Super util cuando en swagger/ postman u otro programa ponen cualquier fecha inexistente, dias, meses.<br>
     * <p>
     * Usar los siguientes patterns que pertenecen a esta Clase y fueron probados:<br>
     * <p>
     * DD_MM_YYYY_SLASH<br>
     * YYYY_MM_DD_SLASH<br>
     * DD_MM_YYYY_DASH<br>
     * YYYY_MM_DD_DASH<br>
     * <p>
     * @exception java.time.format.DateTimeParseException => Dispara la excepcion si no cumple con una fecha real por calendario
     * @author Axel A. Berlot
     * @return void.
     * @since 01-12-2020
     * @version 1.0
     */
    public static void firewallIsRealDate (String date, String pattern, boolean allowNulls, Locale country) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern, country);
        LocalDate pivotDate = null;
        if (allowNulls == true)
            pivotDate = date != null ? LocalDate.parse(date, formatter) : null;
        else
            pivotDate = LocalDate.parse(date, formatter);
    }

    public static java.sql.Date parsearFecha(String fecha, String dateFormat) throws IOException {
        java.sql.Date fechaAltaSql = null;
        if (fecha != null) {
            SimpleDateFormat format = new SimpleDateFormat(dateFormat);
            try {
                Date parsedDateFechaAlta = format.parse(fecha);
                fechaAltaSql = new java.sql.Date(parsedDateFechaAlta.getTime());
            } catch (Exception exc) {
                throw new IOException("Error al parsear la fecha");
            }
        }
        return fechaAltaSql;
    }
    
    /**
     * Metodo que maneja una Exception si la fecha pasada como parametro existe o no en el calendario real.<br>
     * <p>
     * MUY PRECISO, diferencia 28/29 febrero de diferentes años.<br>
     * <p>
     * Ejemplo 20/12/2020 no falla, sin embargo 50/12/2020 si ya que esa fecha no existe por calendario.<br> 
     * <p>
     * Este metodo no es necesario colocarlo antes de la logica ya que solo devuelve un booleano y sirve para validar.
     * <p>
     * Usar los siguientes patterns que pertenecen a esta Clase y fueron probados:<br>
     * <p>
     * Super util cuando en swagger/ postman u otro programa ponen cualquier fecha inexistente, dias, meses.<br>
     * <p>
     * ISO_DATE_TIME_FORMAT_MILLI_SECONDS<br>
     * ISO_DATE_TIME_FORMAT_MICRO_SECONDS<br>
     * ISO_DATE_TIME_FORMAT_MINUTES<br>
     * ISO_DATE_TIME_ZONE_FORMAT_MINUTES<br>
     * <p>
     * Example:<br>
     * <p>
     * String dateTime = convertDateToIsoDateToSpecificPattern(new Date(), ISO_DATE_TIME_ZONE_FORMAT_MINUTES);<br>
     * System.out.println(dateTime);<br>
     * System.out.println(isValidDateTime(dateTime, ISO_DATE_TIME_ZONE_FORMAT_MINUTES));<br>
     * @exception java.time.format.DateTimeParseException => Dispara la excepcion pero es manejada como boolean.
     * @author Axel A. Berlot
     * @return boolean.
     * @since 01-12-2020
     * @version 1.0
     */
    public static boolean isValidDateTime(String dateTime, String pattern) {
        try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
            LocalDateTime d = dateTime != null ? LocalDateTime.parse(dateTime, formatter) : null;
            return true;
        } catch (DateTimeParseException e) {
            return false;
        }
    }

    /**
     * 
     * Solo lanza una excepcion se la fecha o el tiempo no existen, ejemplo 2020 julio 29 hs 72 minutos.<br>
     * <p>
     * Super util cuando en swagger/ postman u otro programa ponen cualquier fecha inexistente, dias, mesesm horas, minutos etc.<br>
     * <p>
     * Punto flojo de este metodo, no tira excepcion si por ejemplo se pasa 30/31 de febrero, pero si rompe si pones 32 de febrero en adelante o pones 61 minutos en la hora. 
     * <p>
     * Se usa como pared ya que su funcion es TIRAR UNA EXCEPCION o no hacer nada
     * <p>
     * Usar los siguientes patterns que pertenecen a esta Clase y fueron probados:<br>
     * ISO_DATE_TIME_FORMAT_MILLI_SECONDS<br>
     * ISO_DATE_TIME_FORMAT_MICRO_SECONDS<br>
     * ISO_DATE_TIME_FORMAT_MINUTES<br>
     * ISO_DATE_TIME_ZONE_FORMAT_MINUTES<br>    
     * <p>
     * Example:
     * <p>
     * String dateTime = convertDateToIsoDateToSpecificPattern(new Date(), ISO_DATE_TIME_ZONE_FORMAT_MINUTES);<br>
     * System.out.println(dateTime);<br>
     * firewallIsRealDateTime(dateTime, ISO_DATE_TIME_ZONE_FORMAT_MINUTES, true);<br>
     * @author Axel A. Berlot
     * @return void.
     * @since 01-12-2020
     * @version 1.0
     */
    public static void firewallIsRealDateTime (String date, String pattern, boolean allowNulls) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern(pattern);
        LocalDateTime pivotDate = null;
        if (allowNulls == true)
            pivotDate = date != null ? LocalDateTime.parse(date, formatter) : null;
        else
            pivotDate = LocalDateTime.parse(date, formatter);
    }

    /**
     * Retorna una fecha en formato String con un patron en particular que se le pasa por parametro.
     * <p>
     * Usar los siguientes patterns que pertenecen a esta Clase y fueron probados:<br>
     * ISO_DATE_TIME_FORMAT_MILLI_SECONDS<br>
     * ISO_DATE_TIME_FORMAT_MICRO_SECONDS<br>
     * ISO_DATE_TIME_FORMAT_MINUTES<br>
     * ISO_DATE_TIME_ZONE_FORMAT_MINUTES<br>    
     * <p>
     * Example:
     * <p>
     * String dateTime = convertDateToIsoDateToSpecificPattern(new Date(), ISO_DATE_TIME_ZONE_FORMAT_MINUTES);<br>
     * System.out.println(dateTime);
     * <p>
     * @author Axel A. Berlot
     * @return String.
     * @since 01-12-2020
     * @version 1.0
     */
    public static String convertDateToIsoDateToSpecificPattern(Date date, String pattern) {
    	DateFormat df = new SimpleDateFormat(pattern); // Quoted "Z" to indicate UTC, no timezone offset
    	return df.format(date);
    }
    
	/**
	 * Convierte de Date a LocalDate en el mismo pais u horario de la computadora.<br>
	 * <p>
	 * @since 01-12-2020
	 * @param date
	 * @return LocalDate
	 * @version 1.0
	 */
    public static LocalDate fromDateToLocalDateByInstant(Date date) {
    	return date.toInstant()
        	      .atZone(ZoneId.systemDefault())
        	      .toLocalDate();
    }
    
    /**
     * Para obtener un ZoneId se puede crear directamente de la clase ZoneId, pasando su identificador, que en este caso es un String<br>
     * <p>
     * Otra opcion es usar el metodo getZoneIdByShortcut() de esta clase, que pasando una key de 3 digitos Mayusculas, "si existe", entonces devolvera el id de la ZoneId.<br>  
     * <p>
     * Ejemplo de creacion de ZoneId aislado.<br>
     * <p>
     * ZoneId zoneIdMx = ZoneId.of("America/Mexico_City");
     * ZoneId zoneIdAm = ZoneId.of("Europe/Amsterdam");
     * <p>
     * Ejemplo<br>
     * <p>
     * fromDateToLocalDateByInstant(new Date(), getZoneIdByShortcut("CAT"));
     * <p>
     * Ejemplos de codigos:<br>
     * CAT => Africa/Harare<br>
     * AGT => America/Argentina/Buenos_Aires<br>
     * AET => Australia/Sydney<br>
     * BET => America/Sao_Paulo<br>
     * PNT => America/Phoenix<br>
     * <p>
     * Ver todos => getZoneIdAsMap();
     * @param date
     * @param zoneId
     * @return
     */
    public static LocalDate fromDateToLocalDateByInstant(Date date, ZoneId zoneId) {
    	return date.toInstant()
        	      .atZone(zoneId)
        	      .toLocalDate();
    }
    
    /**
     * Convierte de Date a LocalDate en el mismo pais u horario de la computadora.<br>
     * <p>
     * @since 01-12-2020
     * @param date
     * @return LocalDate
     * @version 1.0
     */
    public static LocalDate fromDateToLocalDateByMilliSeconds(Date date) {
        return Instant.ofEpochMilli(date.getTime())
          .atZone(ZoneId.systemDefault())
          .toLocalDate();
    }
    
    /**
     * Convierte de Date a LocalDate en el mismo pais u horario de la computadora.<br>
     * <p>
     * Hace diferencia en el pais o region que se encuentra agregando el parametro ZoneId.
     * <p>
     * Ejemplo de creacion de ZoneId aislado.<br>
     * <p>
     * ZoneId zoneIdMx = ZoneId.of("America/Mexico_City");
     * ZoneId zoneIdAm = ZoneId.of("Europe/Amsterdam");
     * <p>
     * Ejemplo<br>
     * <p>
     * fromDateToLocalDateByMilliSeconds(new Date(), getZoneIdByShortcut("CAT"));
     * <p>
     * Ejemplos de codigos:<br>
     * CAT => Africa/Harare<br>
     * AGT => America/Argentina/Buenos_Aires<br>
     * AET => Australia/Sydney<br>
     * BET => America/Sao_Paulo<br>
     * PNT => America/Phoenix<br>
     * <p>
     * Ver todos => getZoneIdAsMap();
     * @since 01-12-2020
     * @param date
     * @return LocalDate
     * @version 1.0
     */    
    public static LocalDate fromDateToLocalDateByMilliSeconds(Date date, ZoneId zoneId) {
        return Instant.ofEpochMilli(date.getTime())
          .atZone(zoneId)
          .toLocalDate();
    }
    
    /**
     * Convierte de Date a LocalDate en el mismo pais u horario de la computadora.<br>
     * <p>
     * @since 01-12-2020
     * @param date
     * @return LocalDate
     * @version 1.0
     */
    public static LocalDateTime fromDateToLocalDateTimeByInstant(Date date) {
        return date.toInstant()
          .atZone(ZoneId.systemDefault())
          .toLocalDateTime();
    }

    /**
     * Convierte de Date a LocalDate en el mismo pais u horario de la computadora.<br>
     * <p>
     * Hace diferencia en el pais o region que se encuentra agregando el parametro ZoneId.
     * <p>
     * Ejemplo de creacion de ZoneId aislado.<br>
     * <p>
     * ZoneId zoneIdMx = ZoneId.of("America/Mexico_City");
     * ZoneId zoneIdAm = ZoneId.of("Europe/Amsterdam");
     * <p>
     * Ejemplo<br>
     * <p>
     * fromDateToLocalDateTimeByInstant(new Date(), getZoneIdByShortcut("CAT"));
     * <p>
     * Ejemplos de codigos:<br>
     * CAT => Africa/Harare<br>
     * AGT => America/Argentina/Buenos_Aires<br>
     * AET => Australia/Sydney<br>
     * BET => America/Sao_Paulo<br>
     * PNT => America/Phoenix<br>
     * <p>
     * Ver todos => getZoneIdAsMap();
     * @since 01-12-2020
     * @param date
     * @return LocalDate
     * @version 1.0
     */
    public static LocalDateTime fromDateToLocalDateTimeByInstant(Date date, ZoneId zoneId) {
        return date.toInstant()
          .atZone(zoneId)
          .toLocalDateTime();
    }

    /**
     * Convierte de Date a LocalDate en el mismo pais u horario de la computadora.<br>
     * <p>
     * @since 01-12-2020
     * @param date
     * @return LocalDate
     * @version 1.0
     */
    public static LocalDateTime fromDateToLocalDateTimeByMilisecond(Date date) {
        return Instant.ofEpochMilli(date.getTime())
          .atZone(ZoneId.systemDefault())
          .toLocalDateTime();
    }
    
    /**
     * Convierte de Date a LocalDate en el mismo pais u horario de la computadora.<br>
     * <p>
     * Hace diferencia en el pais o region que se encuentra agregando el parametro ZoneId.
     * <p>
     * Ejemplo de creacion de ZoneId aislado.<br>
     * <p>
     * ZoneId zoneIdMx = ZoneId.of("America/Mexico_City");
     * ZoneId zoneIdAm = ZoneId.of("Europe/Amsterdam");
     * <p>
     * Ejemplo<br>
     * <p>
     * fromDateToLocalDateTimeByMilisecond(new Date(), getZoneIdByShortcut("CAT"));
     * <p>
     * Ejemplos de codigos:<br>
     * CAT => Africa/Harare<br>
     * AGT => America/Argentina/Buenos_Aires<br>
     * AET => Australia/Sydney<br>
     * BET => America/Sao_Paulo<br>
     * PNT => America/Phoenix<br>
     * <p>
     * Ver todos => getZoneIdAsMap();
     * @since 01-12-2020
     * @param date
     * @return LocalDate
     * @version 1.0
     */
    public static LocalDateTime fromDateToLocalDateTimeByMilisecond(Date date, ZoneId zoneId) {
        return Instant.ofEpochMilli(date.getTime())
          .atZone(zoneId)
          .toLocalDateTime();
    }
    
    /**
     * Convierte de LocalDate a java.sql.Date<br>
     * @param date
     * @author Axel A. Berlot
     * @since 01-12-2020
     * @return java.sql.Date
     */
    public static Date fromLocalDateToSqlDate(LocalDate date) {
	    return java.sql.Date.valueOf(date);
    }
    
    /**
     * Convierte de LocalDate a java.util.Date<br>
     * @param date
     * @author Axel A. Berlot
     * @since 01-12-2020
     * @return java.util.Date
     */
    public static Date fromLocalDateToUtilDate(LocalDate date) {
        return java.util.Date.from(date.atStartOfDay()
          .atZone(ZoneId.systemDefault())
          .toInstant());
    }
    
    /**
     * Convierte de LocalDate a java.util.Date<br>
     * <p>
     * Hace diferencia en el pais o region que se encuentra agregando el parametro ZoneId.
     * <p>
     * Ejemplo de creacion de ZoneId aislado.<br>
     * <p>
     * ZoneId zoneIdMx = ZoneId.of("America/Mexico_City");
     * ZoneId zoneIdAm = ZoneId.of("Europe/Amsterdam");
     * <p>
     * Ejemplo<br>
     * <p>
     * fromDateToLocalDateTimeByMilisecond(new Date(), getZoneIdByShortcut("CAT"));
     * <p>
     * Ejemplos de codigos:<br>
     * CAT => Africa/Harare<br>
     * AGT => America/Argentina/Buenos_Aires<br>
     * AET => Australia/Sydney<br>
     * BET => America/Sao_Paulo<br>
     * PNT => America/Phoenix<br>
     * <p>
     * Ver todos => getZoneIdAsMap();
     * @param date
     * @author Axel A. Berlot
     * @since 01-12-2020
     * @return java.util.Date
     */
    public static Date fromLocalDateToUtilDate(LocalDate date, ZoneId zoneId) {
        return java.util.Date.from(date.atStartOfDay()
          .atZone(zoneId)
          .toInstant());
    }

    /**
     * Convierte de LocalDate a java.sql.Date / Timestamp<br>
     * @param date
     * @author Axel A. Berlot
     * @since 01-12-2020
     * @return java.sql.Date
     */
    public static LocalDateTime fromSqlDateToLocalDateTime(java.sql.Date date) {
        return new java.sql.Timestamp(date.getTime()).toLocalDateTime();
    }

    /**
     * Convierte de LocalDateTime a java.util.Date<br>
     * @param date
     * @author Axel A. Berlot
     * @since 01-12-2020
     * @return java.util.Date
     */
    public static Date fromLocalDateTimeToUtilDateByInstant(LocalDateTime date) {
        return java.util.Date.from(date.atZone(ZoneId.systemDefault()).toInstant());
    }
    
    /**
     * Si la fecha que se pasa por parametro tiene la estructura ISO_LOCAL_DATE => ejemplo: "2016-08-16"<br>
     * Entonces no es necesario pasar un pattern de parametro, por ende este metodo solo recibe de parametro fechas con el formato:<br>
     * <p>
     * "yyyy-MM-dd"
     * @author Axel A. Berlot
     * @since 01-12-2020
     * @param dateDashSeparator
     * @return LocalDate
     */
    public static LocalDate fromStringToLocalDate(String dateDashSeparator) {
        return LocalDate.parse(dateDashSeparator);
    }
    
    /**
     * Recibe como parametro la fecha del formato deseado y los formatos posibles son los siguientes:<br>
     * <p>
     * DD_MM_YYYY_SLASH<br>
     * YYYY_MM_DD_SLASH<br>
     * DD_MM_YYYY_DASH<br>
     * YYYY_MM_DD_DASH<br>
     * <p>
     * Las mismas nombradas estan disponibles como variables finales para su uso en esta clase.
     * @author Axel A. Berlot
     * @since 01-12-2020
     * @param dateDashSeparator
     * @return LocalDate
     */
    public static LocalDate fromStringToLocalDate(String date, String formatter) {
    	  return LocalDate.parse(date, DateTimeFormatter.ofPattern(formatter));
    }
    
    /**
     * Retorna un ZoneId en particular que sirve para configurar horas en paises externos.<br>
     * <p>
     * Recorre un Map.<br>
     * idCountry corresponde al String de las key (3 letras MAYUSCULAS) - value (id as String)<br>
     * <p>
     *  @exception NullPointerException => si lo pasado por parametro no lo encuentra en las key que tiene el Map.<br>
     * <p> 
     * Ejemplos de codigos:<br>
     * CAT => Africa/Harare<br>
     * AGT => America/Argentina/Buenos_Aires<br>
     * AET => Australia/Sydney<br>
     * BET => America/Sao_Paulo<br>
     * PNT => America/Phoenix<br>
     * <p>
     * Ver todos => getZoneIdAsMap();
     * @author Axel A. Berlot
     * @since 01-12-2020
     * @version 1.0
     * @return
     */
    public static ZoneId getZoneIdByShortcut(String idCountry) {
    	return ZoneId.of(ZoneId.SHORT_IDS.get(idCountry));
    }
    
    /**
     * Retorna => Lista Set para chequear cuales son las ZoneId, sin embargo no tiene indicador de tres letras Mayusculas<br>
     * <p>
     * Sirven para configurar un pais diferente al que usa el defaultSystem().<br>
     * <p>
     * @author Axel A. Berlot
     * @since 01-12-2020
     * @version 1.0
     * @return Set.
     */    
    public static Set<String> getZoneIdAsSet() {
    	return ZoneId.getAvailableZoneIds();
    }
    
    /**
     * Retorna => List para chequear cuales son las ZoneId, sin embargo no tiene indicador de tres letras Mayusculas<br>
     * <p>
     * Sirven para configurar un pais diferente al que usa el defaultSystem().<br>
     * <p>
     * @author Axel A. Berlot
     * @since 01-12-2020
     * @version 1.0
     * @return List.
     */    
    public static List<String> getZoneIdAsList() {
    	List<String> list = new ArrayList<>(ZoneId.getAvailableZoneIds());
    	return list;
    }

    
    /**
     * Retorna => Map para chequear cuales son las ZoneId y su indicador para buscar en el metodo getZoneIdByShortcut(idString)<br>
     * <p>
     * Devuelve keys de tres letras mayusculas values => id en forma de String<br>
     * <p>
     * Sirven para configurar un pais diferente a donde esta alojado el sistema, que usa el defaultSystem().<br>
     * <p>
     * Recorre un Map devolviendo key, value.
     * @author Axel A. Berlot
     * @since 01-12-2020
     * @version 1.0
     * @return Map.
     */
    public static Map<String, String> getZoneIdAsMap() {
    	return ZoneId.SHORT_IDS;
    }
    
    /**
     * Retorna => Retorna un String con el formato de la fecha pasado por parametro<br>
     * <p>
     * @author Axel A. Berlot
     * @since 01-12-2020
     * @version 1.0
     * @return String.
     */
    public static String formatLocaldateTimeToAnyPattern(LocalDate date, String pattern) {
    	return DateTimeFormatter.ofPattern(pattern).format(date);
    }
    
    /**
     * Retorna => Retorna una lista de idString ZoneId's filtrado por busuqeda de String.<br>
     * <p>
     * Puede pasarse por ejemplo => "America" como parametro y deberia devolver todos los ZoneId's que tengan America en sus ZoneId 's, o pasar "Mendoza" y devolveria los matches.<br>
     * <p>
     * @author Axel A. Berlot
     * @since 01-12-2020
     * @version 1.0
     * @return List.
     */
    public static List<String> getZoneIdsByFilterString(String continentOrRegion) {
    	return getZoneIdAsList().stream().filter( x -> x.contains(continentOrRegion) ).collect(Collectors.toList());
    }
    
    public static void main(String[] args) {
    }

}
