## Seminar 1 - Diary API 구현 

### DiaryController

- 역할
  - controller에서는 사용자 요청을 받고 사용자 입력값이 유효한지 확인합니다.
  - 서비스로 요청을 전달합니다.
- 코드
  - 유효한 입력값인지 확인합니다.
  - 잘못된 입력에 대해서는 IllegalArgumentException을 던집니다. 
  ```java
    private void validateDiaryBody(String body) {
        if (body.length() > MAX_DIARY_LENGTH) {
            throw new IllegalArgumentException("일기의 내용을 " + MAX_DIARY_LENGTH + "자 이내로 입력해주세요.");
        }
    }

    private long parseId(String id) {
        try {
            return Long.parseLong(id);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("유효한 숫자가 아닙니다. 숫자를 입력해주세요.");
        }
    }
  ```
  - 입력받은 id를 long type으로 변환하여 서비스로 요청을 전달합니다.
  ```java
      final void delete(final String id) {
        final long parsedId = parseId(id);
        diaryService.deleteDiary(parsedId);
    }

    final void patch(final String id, final String body) {
        final long parsedId = parseId(id);
        validateDiaryBody(body);
        diaryService.editDiary(parsedId, body);
    }
  ```
  
### DiaryService

- 역할
  - 비즈니스 로직을 처리합니다.
  - 데이터베이스와 상호작용하기 위해 레포지토리를 호출합니다.
- 코드
  - 레포지토리가 반환한 값을 출력합니다.
    ```java
      void deleteDiary(final Long id){
        final String deletedValue = diaryRepository.delete(id);
        System.out.println("삭제 완료 - " + id + " : " + deletedValue);
    }

      void editDiary(final Long id, final String body){
          final String originalBody =  diaryRepository.edit(id, body);
          System.out.println("수정 완료 - " + id + " : " + originalBody + " -> " + body);
      }
    ```

### DiaryRepository

- 역할
  - 데이터를 저장하고 검색하는 기능을 담당합니다. 
- 코드
  - DB에 존재하는 값인지 확인합니다. 
  - 찾고자 하는 요소가 없으므로 NoSuchElementException을 던집니다. 
    ```java
    private void checkDiaryExists(long id) {
        if (!storage.containsKey(id)) {
            throw new NoSuchElementException("유효한 ID가 아닙니다.");
        }
    }
    ```
  - 삭제 시 id를 연속적인 숫자로 유지합니다.
    ```java
      String delete(final long id){
          checkDiaryExists(id);
          final String deletedValue =  storage.get(id);
          final long lastIndex = numbering.longValue();

          for (long index=id+1; index <= lastIndex; index++){
              final String newValue = storage.get(index);
              storage.put(index-1, newValue);
          }

          storage.remove(lastIndex);
          numbering.decrementAndGet();
          return deletedValue;
      }
      ```
  - 변경 시, 존재하는 id인지 확인하고 덮어씁니다. 
    ```java
        String edit(final long id, final String body){
        checkDiaryExists(id);
        return storage.put(id, body);
    }
    ```

### Main

- 오류 출력을 위해 다음과 같은 코드를 추가하였습니다.
```java
    catch (Exception e) {
        ConsoleIO.printLine("오류 발생: " + e.getMessage());
    }
```

