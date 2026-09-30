---
name: scaffold-package-by-pattern
description: Create a new package in a Kotlin/Spring Boot project by mirroring the structure and conventions of an existing reference package
source: auto-skill
extracted_at: '2026-09-29T18:57:49.307Z'
---

## Procedure

When asked to create a new package (e.g., `finance`) based on an existing one (e.g., `tasks`):

### 1. Discover the reference structure

Read all files in the reference package to understand the full pattern:
- `controller/<Name>Controller.kt` — REST endpoints, dependency injection via constructor
- `service/<Name>Service.kt` — business logic, UUID generation, repository calls
- `repository/<Name>Repository.kt` — `CrudRepository` with `@Repository`
- `model/inner/<Name>.kt` — domain data class with `from(entity, relatedEntities)` companion
- `model/dto/<Name>Dto.kt` — mirrors inner model
- `model/dto/<Name>CreateRequestDto.kt` — input DTO with optional validation
- `model/dto/<Name>CreateResponseDto.kt` — wraps `Dto.from(innerModel)`

### 2. Read existing entities

Check if entity classes already exist in the target package. If so, reuse them.
If not, create entity files matching JPA conventions (`@Entity`, `@Table`, `@Id`).

### 3. Create files in order

1. **Inner model** (`model/inner/<Name>.kt`) — data class with nested sub-models, `companion object from()` that maps entity → domain. Use import alias (`import com...Entity as Entity`) if inner class name collides with entity name.
2. **DTOs** — `Dto`, `CreateRequestDto`, `CreateResponseDto`, plus any sub-request DTOs in nested packages (e.g., `change/`, `completion/`).
3. **Repositories** — `CrudRepository` interfaces with `@Repository`. Add custom query methods if needed (e.g., `findAllByXxxId`).
4. **Service** — `@Service` class with constructor injection of `UUIDGenerator` + repositories. Mirror the reference service methods.
5. **Controller** — `@RestController` with constructor injection. Mirror reference endpoints (POST create, GET all, GET by id, DELETE all, POST sub-resource).

### 4. Resolve naming collisions

When the inner model class name is the same as the entity class name (e.g., both `Balance`), use import alias:
```kotlin
import com...model.entity.Balance as BalanceEntity
```

### 5. Validate

Run `gradlew compileKotlin` to verify no compilation errors or warnings. Fix any warnings (e.g., unnecessary elvis on non-nullable types).

## Key conventions to follow

- UUIDs generated via `UUIDGenerator` interface + `DefaultUUIDGenerator` component
- Repositories extend `CrudRepository<Entity, UUID>`
- Inner models use `companion object from(entity, relatedEntities)` for mapping
- DTOs use `companion object from(innerModel)` for mapping
- Service methods throw `IllegalArgumentException` for not-found cases
- Controller endpoints mirror reference package exactly (same HTTP methods, paths, request/response types)
- Nested sub-resources use sub-packages (e.g., `dto/change/`, `dto/completion/`)
