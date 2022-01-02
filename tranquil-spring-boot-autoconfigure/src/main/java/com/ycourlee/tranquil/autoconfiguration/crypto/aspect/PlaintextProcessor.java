package com.ycourlee.tranquil.autoconfiguration.crypto.aspect;


import com.ycourlee.tranquil.autoconfiguration.crypto.AesCrypto;
import com.ycourlee.tranquil.autoconfiguration.crypto.annotation.CryptoAnnotationMetadata;
import com.ycourlee.tranquil.autoconfiguration.crypto.annotation.Plaintext;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

/**
 * @author yongjiang
 * @date 2021.12.17
 */
class PlaintextProcessor extends AbstractCryptoAnnotationProcess {

    public PlaintextProcessor(AesCrypto aesCrypto) {
        super(aesCrypto);
    }

    @Override
    public void processInput(Object obj, List<String> enableGroups) {
        Map<Field, Annotation> fields = findAnnotatedFields(obj.getClass(), Plaintext.class);
        for (Map.Entry<Field, Annotation> entry : fields.entrySet()) {
            Field field = entry.getKey();
            Plaintext annoMeta = ((Plaintext) entry.getValue());
            if (annoMeta == null || shouldBeIgnore(annoMeta.group(), enableGroups)) {
                continue;
            }
            processField(obj, field, CryptoAnnotationMetadata.from(annoMeta));
        }
    }
}
