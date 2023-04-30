package com.andrewsha.marketplace.storage;

import java.io.File;
import java.io.IOException;
import java.util.Collection;
import java.util.UUID;
import java.util.stream.Collectors;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.andrewsha.marketplace.exception.StorageServiceException;

@Service
public class StorageService {

    @Value("${server.address}")
    String serverAddress;
    @Value("${server.port}")
    String serverPort;
    @Value("${upload.path.products}")
    String uploadPath;

    @Autowired
    private Logger logger;

    public File initUploadDir(String path) {
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        return dir;
    }

    public String store(MultipartFile file, String uploadDirPath) {
        this.initUploadDir(uploadDirPath);
        String newFileName = UUID.randomUUID().toString() + "." + file.getOriginalFilename();
        try {
            file.transferTo(new File(uploadDirPath + "/" + newFileName));
        } catch (IllegalStateException e) {
            this.logger.warn(e.getMessage());
            throw new StorageServiceException(e.getMessage());
        } catch (IOException e) {
            this.logger.warn(e.getMessage());
            throw new StorageServiceException(e.getMessage());
        }
        return newFileName;
    }

    public Collection<String> storeAll(Collection<MultipartFile> files, String uploadDirPath) {
        this.initUploadDir(uploadDirPath);
        return files.stream().map(e -> {
            String newFileName = UUID.randomUUID().toString() + "." + e.getOriginalFilename();
            try {
                e.transferTo(new File(uploadDirPath + "/" + newFileName));
            } catch (IllegalStateException ex) {
                this.logger.warn(ex.getMessage());
                throw new StorageServiceException(ex.getMessage());
            } catch (IOException ex) {
                this.logger.warn(ex.getMessage());
                throw new StorageServiceException(ex.getMessage());
            }
            return newFileName;
        }).collect(Collectors.toList());
    }
}
