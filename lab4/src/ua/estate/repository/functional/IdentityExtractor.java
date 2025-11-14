package ua.estate.repository.functional;

@FunctionalInterface
public interface IdentityExtractor<T> {

    String getIdentity(T entity);
}
