package com.sievex.crawler.controller;

import com.sievex.constants.ApiResponseConstants;
import com.sievex.crawler.entity.StatusType;
import com.sievex.crawler.service.StatusTypeService;
import com.sievex.dto.DataApiResponse;
import com.sievex.dto.request.StatusTypeRequestDto;
import com.sievex.dto.response.StatusTypeResponseDto;
import jakarta.validation.Valid;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(StatusTypeController.BASE_URL)
public class StatusTypeController {
    public static final String BASE_URL = "status-type";
    private static final Logger logger = LoggerFactory.getLogger(StatusTypeController.class);

    private final StatusTypeService statusTypeService;

    @Autowired
    public StatusTypeController(StatusTypeService statusTypeService) {
        this.statusTypeService = statusTypeService;
    }

    private StatusType toRequestEntity(StatusTypeRequestDto statusTypeRequestDto) {
        StatusType entity = new StatusType();
        if (statusTypeRequestDto == null) {
            return null;
        }
        entity.setName(statusTypeRequestDto.getName());
        entity.setAlias(statusTypeRequestDto.getAlias());
        entity.setDescription(statusTypeRequestDto.getDescription());
        entity.setStatus(statusTypeRequestDto.getStatus());
        return entity;
    }

    private StatusTypeResponseDto toResponseEntity(StatusType statusType) {
        StatusTypeResponseDto entity = new StatusTypeResponseDto();
        entity.setName(statusType.getName());
        entity.setAlias(statusType.getAlias());
        entity.setDescription(statusType.getDescription());
        entity.setStatus(statusType.getStatus());
        return entity;
    }

    @GetMapping("/get/all-status-type")
    public ResponseEntity<DataApiResponse<List<StatusTypeResponseDto>>> getAllStatusType() {
        List<StatusType> allStatusTypes = statusTypeService.getAllStatusTypes();
        if (allStatusTypes.isEmpty()) {
            logger.error("No status type data found");
            return new ResponseEntity<>(new DataApiResponse<>(ApiResponseConstants.STATUS_FAILURE, ApiResponseConstants.CODE_NOT_FOUND, ApiResponseConstants.MSG_READ_NOT_FOUND), HttpStatus.NOT_MODIFIED);
        }
        List<StatusTypeResponseDto> dtoList =  allStatusTypes.stream().map(this::toResponseEntity).toList();
        return new ResponseEntity<>(new DataApiResponse<>(ApiResponseConstants.STATUS_SUCCESS, ApiResponseConstants.CODE_OK, ApiResponseConstants.MSG_READ_SUCCESS, dtoList), HttpStatus.OK);
    }

    @GetMapping("/get/{alias}")
    public ResponseEntity<DataApiResponse<StatusTypeResponseDto>> getStatusTypeByAlias(@PathVariable("alias") String alias) {
        StatusType statusTypes = statusTypeService.getStatusTypeByAlias(alias);
        if (statusTypes == null) {
            logger.error("No status type data found");
            return new ResponseEntity<>(new DataApiResponse<>(ApiResponseConstants.STATUS_FAILURE, ApiResponseConstants.CODE_NOT_FOUND, ApiResponseConstants.MSG_READ_NOT_FOUND), HttpStatus.NOT_MODIFIED);
        }
        StatusTypeResponseDto statusTypeResponseDto = toResponseEntity(statusTypes);
        return new ResponseEntity<>(new DataApiResponse<>(ApiResponseConstants.STATUS_SUCCESS, ApiResponseConstants.CODE_OK, ApiResponseConstants.MSG_READ_SUCCESS, statusTypeResponseDto), HttpStatus.OK);
    }

    @PostMapping("/create")
    public ResponseEntity<DataApiResponse<StatusTypeResponseDto>> createStatusType(@Valid @RequestBody StatusTypeRequestDto statusTypesRequest) {
        StatusType createdStatusType = statusTypeService.saveStatusType(toRequestEntity(statusTypesRequest));
        if (createdStatusType == null) {
            logger.error("Failed to create status type data");
            return new ResponseEntity<>(new DataApiResponse<>(ApiResponseConstants.STATUS_FAILURE, ApiResponseConstants.CODE_BAD_REQUEST, ApiResponseConstants.MSG_CREATE_FAILED), HttpStatus.BAD_REQUEST);
        }
        StatusTypeResponseDto createdStatusTypeResponse = toResponseEntity(createdStatusType);
        logger.info("Status type data created successfully");
        return new ResponseEntity<>(new DataApiResponse<>(ApiResponseConstants.STATUS_SUCCESS, ApiResponseConstants.CODE_OK, ApiResponseConstants.MSG_CREATE_SUCCESS, createdStatusTypeResponse), HttpStatus.CREATED);
    }

    @PutMapping("/update")
    public ResponseEntity<DataApiResponse<StatusTypeResponseDto>> updateStatusType(@RequestBody StatusTypeRequestDto statusTypesRequest) {
        StatusType updatedStatusType = statusTypeService.updateStatusType(toRequestEntity(statusTypesRequest));
        if (updatedStatusType == null) {
            logger.error("Failed to update status type data");
            return new ResponseEntity<>(new DataApiResponse<>(ApiResponseConstants.STATUS_FAILURE, ApiResponseConstants.CODE_BAD_REQUEST, ApiResponseConstants.MSG_UPDATE_FAILED), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new DataApiResponse<>(ApiResponseConstants.STATUS_SUCCESS, ApiResponseConstants.CODE_OK, ApiResponseConstants.MSG_UPDATE_SUCCESS), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<DataApiResponse<Void>> deleteStatusTypeById(@PathVariable Long id) {
        boolean isDeleted = statusTypeService.deleteStatusType(id);
        if (!isDeleted) {
            logger.error("Failed to delete status type data");
            return new ResponseEntity<>(new DataApiResponse<>(ApiResponseConstants.STATUS_FAILURE, ApiResponseConstants.CODE_BAD_REQUEST, ApiResponseConstants.MSG_DELETE_FAILED), HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(new DataApiResponse<>(ApiResponseConstants.STATUS_SUCCESS, ApiResponseConstants.CODE_OK, ApiResponseConstants.MSG_DELETE_SUCCESS), HttpStatus.OK);
    }
}
