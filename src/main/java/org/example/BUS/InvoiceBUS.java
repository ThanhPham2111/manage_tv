package org.example.BUS;

import java.util.ArrayList;

import org.example.DAO.InvoiceDAO;
import org.example.DTO.InvoiceDTO;

public class InvoiceBUS {
    private InvoiceDAO invoiceDAO;

    public InvoiceBUS() {
        invoiceDAO = new InvoiceDAO();
    }

    public ArrayList<InvoiceDTO> getAllInvoices() {
        return invoiceDAO.getAllInvoices();
    }

    public InvoiceDTO getInvoice(String maHoaDon) {
        return invoiceDAO.getInvoice(maHoaDon);
    }
}