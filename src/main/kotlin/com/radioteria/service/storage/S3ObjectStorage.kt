package com.radioteria.service.storage

import com.amazonaws.auth.BasicAWSCredentials
import com.radioteria.config.spring.logging.Logging
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty
import org.springframework.stereotype.Service
import java.io.InputStream
import com.amazonaws.services.s3.AmazonS3Client
import com.amazonaws.services.s3.model.ObjectMetadata


@Logging
@ConditionalOnProperty("radioteria.storage", havingValue = "s3")
@Service
class S3ObjectStorage(
        @Value("radioteria.storage.s3.accessKey") val accessKey: String,
        @Value("radioteria.storage.s3.secretKey") val secretKey: String,
        @Value("radioteria.storage.s3.bucketName") val bucketName: String
) : ObjectStorage {

    private val s3Client: AmazonS3Client = AmazonS3Client(BasicAWSCredentials(accessKey, secretKey))

    override fun has(key: String): Boolean {
        return s3Client.doesObjectExist(bucketName, key)
    }

    override fun get(key: String): ObjectStorage.Object {
        val s3Object = s3Client.getObject(bucketName, key)
        val objectLength = s3Object.objectMetadata.contentLength
        val objectMetadata = Metadata(contentType = s3Object.objectMetadata.contentType)

        return ObjectStorage.Object(key, objectLength, objectMetadata, { s3Object.objectContent })
    }

    override fun delete(key: String) {
        s3Client.deleteObject(bucketName, key)
    }

    override fun put(key: String, inputStream: InputStream, metadata: Metadata) {
        val objectMetadata = ObjectMetadata()
        objectMetadata.contentType = metadata.contentType
        s3Client.putObject(bucketName, key, inputStream, objectMetadata)
    }

}