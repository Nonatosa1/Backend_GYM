package com.gym.api.service.impl;

import com.gym.api.dto.ReporteIngresosDTO;
import com.gym.api.dto.TransaccionDTO;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.time.format.DateTimeFormatter;

@Service
public class ReporteIngresosExcelService {

    private static final DateTimeFormatter FORMATO_FECHA = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    private static final DateTimeFormatter FORMATO_FECHA_HORA = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");

    public byte[] generarExcel(ReporteIngresosDTO reporte) throws IOException {
        try (Workbook workbook = new XSSFWorkbook();
             ByteArrayOutputStream out = new ByteArrayOutputStream()) {

            Sheet sheet = workbook.createSheet("Reporte de Ingresos");

            // Estilos reutilizables
            CellStyle estiloTitulo = crearEstiloTitulo(workbook);
            CellStyle estiloSubtitulo = crearEstiloSubtitulo(workbook);
            CellStyle estiloEncabezadoTabla = crearEstiloEncabezadoTabla(workbook);
            CellStyle estiloCelda = crearEstiloCelda(workbook);
            CellStyle estiloMoneda = crearEstiloMoneda(workbook);
            CellStyle estiloResumenEtiqueta = crearEstiloResumenEtiqueta(workbook);
            CellStyle estiloResumenValor = crearEstiloResumenValor(workbook);

            int filaActual = 0;

            // ===== TÍTULO =====
            Row filaTitulo = sheet.createRow(filaActual++);
            filaTitulo.setHeightInPoints(28);
            Cell celdaTitulo = filaTitulo.createCell(0);
            celdaTitulo.setCellValue("Reporte de Ingresos");
            celdaTitulo.setCellStyle(estiloTitulo);
            sheet.addMergedRegion(new CellRangeAddress(0, 0, 0, 4));

            // ===== PERIODO =====
            Row filaPeriodo = sheet.createRow(filaActual++);
            Cell celdaPeriodo = filaPeriodo.createCell(0);
            celdaPeriodo.setCellValue("Periodo: "
                    + reporte.getFechaInicio().format(FORMATO_FECHA)
                    + " al "
                    + reporte.getFechaFin().format(FORMATO_FECHA));
            celdaPeriodo.setCellStyle(estiloSubtitulo);
            sheet.addMergedRegion(new CellRangeAddress(1, 1, 0, 4));

            filaActual++; // fila en blanco

            // Resumen
            agregarFilaResumen(sheet, filaActual++, "Total ingresado:",
                    "$" + reporte.getTotalIngresado().toString(),
                    estiloResumenEtiqueta, estiloResumenValor);

            agregarFilaResumen(sheet, filaActual++, "Total de transacciones:",
                    reporte.getTotalTransacciones().toString(),
                    estiloResumenEtiqueta, estiloResumenValor);

            agregarFilaResumen(sheet, filaActual++, "Promedio por transacción:",
                    "$" + reporte.getPromedioPago().toString(),
                    estiloResumenEtiqueta, estiloResumenValor);

            filaActual++; // fila en blanco

            // Encabezados
            String[] encabezados = {"ID", "Fecha", "Miembro", "Concepto", "Monto"};
            Row filaEncabezados = sheet.createRow(filaActual++);
            for (int i = 0; i < encabezados.length; i++) {
                Cell celda = filaEncabezados.createCell(i);
                celda.setCellValue(encabezados[i]);
                celda.setCellStyle(estiloEncabezadoTabla);
            }

            // Filas de transacciones
            for (TransaccionDTO t : reporte.getTransacciones()) {
                Row fila = sheet.createRow(filaActual++);

                Cell celdaId = fila.createCell(0);
                celdaId.setCellValue(t.getId());
                celdaId.setCellStyle(estiloCelda);

                Cell celdaFecha = fila.createCell(1);
                celdaFecha.setCellValue(t.getFecha().format(FORMATO_FECHA_HORA));
                celdaFecha.setCellStyle(estiloCelda);

                Cell celdaMiembro = fila.createCell(2);
                celdaMiembro.setCellValue(t.getNombreMiembro());
                celdaMiembro.setCellStyle(estiloCelda);

                Cell celdaConcepto = fila.createCell(3);
                celdaConcepto.setCellValue(t.getConcepto());
                celdaConcepto.setCellStyle(estiloCelda);

                Cell celdaMonto = fila.createCell(4);
                celdaMonto.setCellValue(t.getMonto().doubleValue());
                celdaMonto.setCellStyle(estiloMoneda);
            }

            // Ajuste de ancho de columndas
            for (int i = 0; i < encabezados.length; i++) {
                sheet.autoSizeColumn(i);
            }
            // Damos un poquito de margen extra
            for (int i = 0; i < encabezados.length; i++) {
                sheet.setColumnWidth(i, sheet.getColumnWidth(i) + 1000);
            }

            workbook.write(out);
            return out.toByteArray();
        }
    }

    // Métodos auxiliares

    private void agregarFilaResumen(Sheet sheet, int numFila, String etiqueta, String valor,
                                    CellStyle estiloEtiqueta, CellStyle estiloValor) {
        Row fila = sheet.createRow(numFila);
        Cell celdaEtiqueta = fila.createCell(0);
        celdaEtiqueta.setCellValue(etiqueta);
        celdaEtiqueta.setCellStyle(estiloEtiqueta);

        Cell celdaValor = fila.createCell(1);
        celdaValor.setCellValue(valor);
        celdaValor.setCellStyle(estiloValor);
    }

    private CellStyle crearEstiloTitulo(Workbook wb) {
        CellStyle estilo = wb.createCellStyle();
        Font fuente = wb.createFont();
        fuente.setBold(true);
        fuente.setFontHeightInPoints((short) 16);
        fuente.setColor(IndexedColors.WHITE.getIndex());
        estilo.setFont(fuente);
        estilo.setFillForegroundColor(IndexedColors.DARK_BLUE.getIndex());
        estilo.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        estilo.setAlignment(HorizontalAlignment.CENTER);
        estilo.setVerticalAlignment(VerticalAlignment.CENTER);
        return estilo;
    }

    private CellStyle crearEstiloSubtitulo(Workbook wb) {
        CellStyle estilo = wb.createCellStyle();
        Font fuente = wb.createFont();
        fuente.setItalic(true);
        fuente.setFontHeightInPoints((short) 11);
        estilo.setFont(fuente);
        estilo.setAlignment(HorizontalAlignment.CENTER);
        return estilo;
    }

    private CellStyle crearEstiloEncabezadoTabla(Workbook wb) {
        CellStyle estilo = wb.createCellStyle();
        Font fuente = wb.createFont();
        fuente.setBold(true);
        fuente.setColor(IndexedColors.WHITE.getIndex());
        estilo.setFont(fuente);
        estilo.setFillForegroundColor(IndexedColors.GREY_50_PERCENT.getIndex());
        estilo.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        estilo.setAlignment(HorizontalAlignment.CENTER);
        aplicarBordes(estilo);
        return estilo;
    }

    private CellStyle crearEstiloCelda(Workbook wb) {
        CellStyle estilo = wb.createCellStyle();
        aplicarBordes(estilo);
        return estilo;
    }

    private CellStyle crearEstiloMoneda(Workbook wb) {
        CellStyle estilo = wb.createCellStyle();
        DataFormat formato = wb.createDataFormat();
        estilo.setDataFormat(formato.getFormat("\"$\"#,##0.00"));
        estilo.setAlignment(HorizontalAlignment.RIGHT);
        aplicarBordes(estilo);
        return estilo;
    }

    private CellStyle crearEstiloResumenEtiqueta(Workbook wb) {
        CellStyle estilo = wb.createCellStyle();
        Font fuente = wb.createFont();
        fuente.setBold(true);
        estilo.setFont(fuente);
        return estilo;
    }

    private CellStyle crearEstiloResumenValor(Workbook wb) {
        CellStyle estilo = wb.createCellStyle();
        estilo.setAlignment(HorizontalAlignment.LEFT);
        return estilo;
    }

    private void aplicarBordes(CellStyle estilo) {
        estilo.setBorderTop(BorderStyle.THIN);
        estilo.setBorderBottom(BorderStyle.THIN);
        estilo.setBorderLeft(BorderStyle.THIN);
        estilo.setBorderRight(BorderStyle.THIN);
    }
}
