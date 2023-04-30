package com.andrewsha.marketplace.domain.temp;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.elasticsearch.core.ElasticsearchOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import lombok.AllArgsConstructor;

@RestController
@RequestMapping(path = "api/v1/temp")
@AllArgsConstructor
public class TempController {
 
    private ElasticsearchOperations elasticsearchOperations;
    private TempRepo repo;

    @GetMapping
    public Iterable<TempObject> getAll(){
        return this.repo.findAll();
    }

    @PostMapping
    public TempObject create(@RequestBody TempObject object){
        return this.repo.save(object);
    }

    @GetMapping(path = "/test")
    public TempObject createNative(){
        TempObject tempObject = new TempObject();
        tempObject.setName("name");
        return this.repo.save(tempObject);
    }
}
