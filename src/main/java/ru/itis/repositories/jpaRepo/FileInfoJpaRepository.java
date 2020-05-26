package ru.itis.repositories.jpaRepo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import ru.itis.dto.InformationDto;
import ru.itis.models.FileInfo;

public interface FileInfoJpaRepository extends JpaRepository<FileInfo, Long> {

    @Query("select new ru.itis.dto.InformationDto(fileInfo.realName, fileInfo.size) from FileInfo fileInfo where fileInfo.id = :id")
    InformationDto getFileInfo(@Param("id") Long id);
}
