BUILD_DIR=build
SOURCE_DIR=src
SOURCE_FILES:=$(shell find src -name "*.java")
OUTPUT_FILES:=$(patsubst $(SOURCE_DIR)/%.java,$(BUILD_DIR)/%.class,$(SOURCE_FILES))

$(OUTPUT_FILES): $(SOURCE_FILES)
	javac $(SOURCE_FILES) -d $(BUILD_DIR)

run: $(OUTPUT_FILES)
	java -cp $(BUILD_DIR) Main