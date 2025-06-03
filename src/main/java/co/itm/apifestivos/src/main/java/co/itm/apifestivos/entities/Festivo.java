package co.itm.apifestivos.entities;

import jakarta.persistence.*;

@Entity
@Table(name = "festivo")
public class Festivo {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    @Column(name = "dia")
    private Integer dia;
    
    @Column(name = "mes")
    private Integer mes;
    
    @Column(name = "nombre")
    private String nombre;
    
    @Column(name = "tipo")
    private Integer tipo;
    
    @Column(name = "dias_pascua")
    private Integer diasPascua;
    
    // Constructores
    public Festivo() {}
    
    public Festivo(Integer dia, Integer mes, String nombre, Integer tipo, Integer diasPascua) {
        this.dia = dia;
        this.mes = mes;
        this.nombre = nombre;
        this.tipo = tipo;
        this.diasPascua = diasPascua;
    }
    
    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    
    public Integer getDia() { return dia; }
    public void setDia(Integer dia) { this.dia = dia; }
    
    public Integer getMes() { return mes; }
    public void setMes(Integer mes) { this.mes = mes; }
    
    public String getNombre() { return nombre; }
    public void setNombre(String nombre) { this.nombre = nombre; }
    
    public Integer getTipo() { return tipo; }
    public void setTipo(Integer tipo) { this.tipo = tipo; }
    
    public Integer getDiasPascua() { return diasPascua; }
    public void setDiasPascua(Integer diasPascua) { this.diasPascua = diasPascua; }
}
