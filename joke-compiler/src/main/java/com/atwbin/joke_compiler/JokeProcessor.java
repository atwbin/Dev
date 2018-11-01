package com.atwbin.joke_compiler;

import com.atwbin.joke_annotations.WXPayEntry;
import com.google.auto.service.AutoService;

import java.lang.annotation.Annotation;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.annotation.processing.AbstractProcessor;
import javax.annotation.processing.Filer;
import javax.annotation.processing.ProcessingEnvironment;
import javax.annotation.processing.Processor;
import javax.annotation.processing.RoundEnvironment;
import javax.lang.model.SourceVersion;
import javax.lang.model.element.AnnotationMirror;
import javax.lang.model.element.AnnotationValue;
import javax.lang.model.element.AnnotationValueVisitor;
import javax.lang.model.element.Element;
import javax.lang.model.element.ElementVisitor;
import javax.lang.model.element.ExecutableElement;
import javax.lang.model.element.TypeElement;

@AutoService(Processor.class)
public class JokeProcessor extends AbstractProcessor {

    private Filer filer;

    @Override
    public synchronized void init(ProcessingEnvironment processingEnvironment) {
        super.init(processingEnvironment);
        filer = processingEnvironment.getFiler();
    }

    @Override
    public Set<String> getSupportedAnnotationTypes() {
        Set<String> types = new LinkedHashSet<>();
        for (Class<? extends Annotation> annotation : getSupportedAnnotations()) {
            types.add(annotation.getCanonicalName());
        }

        return types;
    }

    /**
     * 参考了 ButterKnife 的写法
     *
     * @return
     */
    private Set<Class<? extends Annotation>> getSupportedAnnotations() {
        Set<Class<? extends Annotation>> annotations = new LinkedHashSet<>();
        annotations.add(WXPayEntry.class);
        return annotations;
    }


    @Override
    public SourceVersion getSupportedSourceVersion() {
        return SourceVersion.latestSupported();
    }


    @Override
    public boolean process(Set<? extends TypeElement> set, RoundEnvironment roundEnvironment) {

        System.out.println("------------------>");

        //生成一个Class xxx.wxapi.WXPayEntryActivity  extends  BaseWXPayActivity
        generateWXPayCode(roundEnvironment);
        return false;
    }


    private void generateWXPayCode(RoundEnvironment roundEnvironment) {
        WXPayEntryVisitor visitor = new WXPayEntryVisitor();
        visitor.setFiler(filer);
        scanElement(roundEnvironment, WXPayEntry.class, visitor);
    }

    private void scanElement(RoundEnvironment roundEnvironment, Class<? extends Annotation> annotation, AnnotationValueVisitor visitor) {
        Set<? extends Element> elements = roundEnvironment.getElementsAnnotatedWith(annotation);
        for (Element element : elements) {
            List<? extends AnnotationMirror> annotationMirrors = element.getAnnotationMirrors();
            for (AnnotationMirror annotationmirror : annotationMirrors) {
                Map<? extends ExecutableElement, ? extends AnnotationValue> elementValues = annotationmirror.getElementValues();

                for (Map.Entry<? extends ExecutableElement, ? extends AnnotationValue> entry : elementValues.entrySet()) {
                    entry.getValue().accept(visitor, null);
                }
            }
        }
    }

}
