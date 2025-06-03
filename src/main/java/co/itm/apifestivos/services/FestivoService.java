package co.itm.apifestivos.services;

import co.itm.apifestivos.entities.Festivo;
import co.itm.apifestivos.repositories.FestivoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

@Service
public class FestivoService {
    
    @Autowired
    private FestivoRepository festivoRepository;
    
    public String validarFecha(String fecha) {
        try {
            // Validar formato de fecha
            LocalDate fechaLocalDate = LocalDate.parse(fecha, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            int year = fechaLocalDate.getYear();
            int month = fechaLocalDate.getMonthValue();
            int day = fechaLocalDate.getDayOfMonth();
            
            // Obtener festivos
            List<Festivo> festivos = festivoRepository.findAll();
            
            // Calcular domingo de pascua para el año
            LocalDate domingoPascua = calcularDomingoPascua(year);
            
            // Verificar si la fecha es festiva
            for (Festivo festivo : festivos) {
                LocalDate fechaFestivo = calcularFechaFestivo(festivo, year, domingoPascua);
                
                if (fechaFestivo != null && fechaFestivo.equals(fechaLocalDate)) {
                    return String.format("La fecha %s SI es festiva: %s", 
                        fechaLocalDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")), 
                        festivo.getNombre());
                }
            }
            
            return String.format("La fecha %s NO es festiva", 
                fechaLocalDate.format(DateTimeFormatter.ofPattern("dd/MM/yyyy")));
                
        } catch (DateTimeParseException e) {
            return "Fecha no válida";
        } catch (Exception e) {
            return "Error al procesar la fecha";
        }
    }
    
    private LocalDate calcularFechaFestivo(Festivo festivo, int year, LocalDate domingoPascua) {
        switch (festivo.getTipo()) {
            case 1: // Festivo fijo
                return LocalDate.of(year, festivo.getMes(), festivo.getDia());
                
            case 2: // Ley de puente festivo
                LocalDate fechaOriginal = LocalDate.of(year, festivo.getMes(), festivo.getDia());
                return trasladarASiguienteLunes(fechaOriginal);
                
            case 3: // Basado en domingo de pascua
                return domingoPascua.plusDays(festivo.getDiasPascua());
                
            case 4: // Basado en domingo de pascua + ley de puente
                LocalDate fechaPascua = domingoPascua.plusDays(festivo.getDiasPascua());
                return trasladarASiguienteLunes(fechaPascua);
                
            default:
                return null;
        }
    }
    
    private LocalDate trasladarASiguienteLunes(LocalDate fecha) {
        // Si no es lunes, trasladar al siguiente lunes
        if (fecha.getDayOfWeek().getValue() != 1) { // 1 = Lunes
            int diasHastaLunes = 8 - fecha.getDayOfWeek().getValue();
            return fecha.plusDays(diasHastaLunes);
        }
        return fecha;
    }
    
    private LocalDate calcularDomingoPascua(int year) {
        // Algoritmo para calcular el domingo de pascua
        int a = year % 19;
        int b = year % 4;
        int c = year % 7;
        int d = (19 * a + 24) % 30;
        int dias = d + (2 * b + 4 * c + 6 * d + 5) % 7;
        
        // El domingo de ramos es 15 de marzo + dias calculados
        LocalDate domingoRamos = LocalDate.of(year, 3, 15).plusDays(dias);
        
        // El domingo de pascua es 7 días después del domingo de ramos
        return domingoRamos.plusDays(7);
    }
}
