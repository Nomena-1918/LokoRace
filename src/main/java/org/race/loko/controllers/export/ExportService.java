package org.race.loko.controllers.export;

import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.xhtmlrenderer.pdf.ITextRenderer;

import java.io.*;
import java.util.Map;

@Service
public class ExportService {
    public String parseThymeleafTemplate(String templateName , Map<String , Object> objects) {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        Context context = new Context();

        for (Map.Entry<String, Object> entry : objects.entrySet()) {
            context.setVariable(entry.getKey(), entry.getValue());
        }

        return templateEngine.process(templateName, context);
    }

    public void generatePdf(String html , OutputStream writer) throws IOException {
        ITextRenderer renderer = new ITextRenderer();
        renderer.setDocumentFromString(html);
        renderer.layout();
        renderer.createPDF(writer);
    }

    public void exportPdf(String templateName , Map<String , Object> objects , OutputStream writer) throws IOException {
        String html = parseThymeleafTemplate(templateName , objects);
        generatePdf(html  , writer);
    }
}
