package org.example.BUS;

import java.util.ArrayList;

import org.example.DAO.InvoiceDAO;
import org.example.DTO.InvoiceDTO;
import org.example.DTO.InvoiceDetailDTO;

public class InvoiceBUS {
    private InvoiceDAO invoiceDAO;

    public InvoiceBUS() {
        invoiceDAO = new InvoiceDAO();
    }

    public String getMaxInvoiceId() {
        return invoiceDAO.getMaxInvoiceId();
    }

    public ArrayList<InvoiceDTO> getAllInvoices() {
        return invoiceDAO.getAllInvoices();
    }

    public InvoiceDTO getInvoice(String maHoaDon) {
        return invoiceDAO.getInvoice(maHoaDon);
    }

    public boolean addInvoice(InvoiceDTO invoice) {
        return invoiceDAO.addInvoice(invoice);
    }

    public boolean addInvoiceDetail(InvoiceDetailDTO detail) {
        return invoiceDAO.addInvoiceDetail(detail);
    }
}