package sn.dev.crudProduit.doa;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.repository.query.FluentQuery;
import sn.dev.crudProduit.Entities.Produit;

import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public class ProductDaoMemoryImpl implements ProductDoa {

    @Override
    public List<Produit> findAllByDesignationContaining(String designation) {
        return List.of();
    }

    @Override
    public void flush() {

    }

    @Override
    public <S extends Produit> S saveAndFlush(S entity) {
        return null;
    }

    @Override
    public <S extends Produit> List<S> saveAllAndFlush(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public void deleteAllInBatch(Iterable<Produit> entities) {

    }

    @Override
    public void deleteAllByIdInBatch(Iterable<Long> longs) {

    }

    @Override
    public void deleteAllInBatch() {

    }

    @Override
    public Produit getOne(Long aLong) {
        return null;
    }

    @Override
    public Produit getById(Long aLong) {
        return null;
    }

    @Override
    public Produit getReferenceById(Long aLong) {
        return null;
    }

    @Override
    public <S extends Produit> Optional<S> findOne(Example<S> example) {
        return Optional.empty();
    }

    @Override
    public <S extends Produit> List<S> findAll(Example<S> example) {
        return List.of();
    }

    @Override
    public <S extends Produit> List<S> findAll(Example<S> example, Sort sort) {
        return List.of();
    }

    @Override
    public <S extends Produit> Page<S> findAll(Example<S> example, Pageable pageable) {
        return null;
    }

    @Override
    public <S extends Produit> long count(Example<S> example) {
        return 0;
    }

    @Override
    public <S extends Produit> boolean exists(Example<S> example) {
        return false;
    }

    @Override
    public <S extends Produit, R> R findBy(Example<S> example, Function<FluentQuery.FetchableFluentQuery<S>, R> queryFunction) {
        return null;
    }

    @Override
    public <S extends Produit> S save(S entity) {
        return null;
    }

    @Override
    public <S extends Produit> List<S> saveAll(Iterable<S> entities) {
        return List.of();
    }

    @Override
    public Optional<Produit> findById(Long aLong) {
        return Optional.empty();
    }

    @Override
    public boolean existsById(Long aLong) {
        return false;
    }

    @Override
    public List<Produit> findAll() {
        return List.of();
    }

    @Override
    public List<Produit> findAllById(Iterable<Long> longs) {
        return List.of();
    }

    @Override
    public long count() {
        return 0;
    }

    @Override
    public void deleteById(Long aLong) {

    }

    @Override
    public void delete(Produit entity) {

    }

    @Override
    public void deleteAllById(Iterable<? extends Long> longs) {

    }

    @Override
    public void deleteAll(Iterable<? extends Produit> entities) {

    }

    @Override
    public void deleteAll() {

    }

    @Override
    public List<Produit> findAll(Sort sort) {
        return List.of();
    }

    @Override
    public Page<Produit> findAll(Pageable pageable) {
        return null;
    }
}
