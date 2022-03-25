package com.alrz.cursomc.entities;

import com.alrz.cursomc.entities.enums.EstadoPagamento;

import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table(name = "PAGAMENTOCOMCARTAO")
public class PagamentoComCartaoEntity extends PagamentoEntity {
    private Integer numeroDeParcelas;

    public PagamentoComCartaoEntity() {
    }

    public PagamentoComCartaoEntity(Long id, EstadoPagamento estado, PedidoEntity pedido, Integer numeroDeParcelas) {
        super(id, estado, pedido);
        this.numeroDeParcelas = numeroDeParcelas;
    }

    public Integer getNumeroDeParcelas() {
        return numeroDeParcelas;
    }

    public void setNumeroDeParcelas(Integer numeroDeParcelas) {
        this.numeroDeParcelas = numeroDeParcelas;
    }
}
