package org.springseed.oss.minio.service;

import java.util.function.Consumer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springseed.oss.minio.util.MinioArgsUtils;
import org.springseed.oss.minio.util.MinioExceptionUtil;
import org.springseed.oss.minio.util.MinioUtils;

import io.minio.MinioClient;
import io.minio.RemoveObjectArgs;
import lombok.extern.slf4j.Slf4j;

/**
 * remove操作
 * 
 * @author PinWei Wan
 * @since 1.0.0
 */
@Slf4j
@Service
public class RemoveOptService {
    @Autowired
    private MinioClient client;

    /**
     * 删除对象
     * 
     * @param bucket
     * @param objectId
     * @param consumer
     */
    public void remove(final String bucket, final String objectId, final Consumer<RemoveObjectArgs.Builder> consumer) {
        final String minioId = MinioUtils.minioId(objectId);
        try {
            final RemoveObjectArgs.Builder builder = RemoveObjectArgs.builder()
                    .bucket(bucket)
                    .object(minioId);

            if (consumer != null) {
                consumer.accept(builder);
            }

            final RemoveObjectArgs args = builder.build();
            if (log.isDebugEnabled()) {
                log.debug("RemoveObjectArgs={}", MinioArgsUtils.printRemoveObjectArgs(args));
            }

            client.removeObject(args);
        }catch (Exception e) {
            throw MinioExceptionUtil.wapper(e);
        }
    }

    /**
     * 删除对象
     * 
     * @param bucket
     * @param objectId
     */
    public void remove(final String bucket, final String objectId) {
        remove(bucket, objectId, null);
    }
}
