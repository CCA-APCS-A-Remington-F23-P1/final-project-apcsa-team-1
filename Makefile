BUILD_DIR=build
SOURCE_DIR=src
SOURCE_FILES:=$(shell find src -name "*.java")
OUTPUT_FILES:=$(patsubst $(SOURCE_DIR)/%.java,$(BUILD_DIR)/%.class,$(SOURCE_FILES))

.PHONY: images package run clean

$(OUTPUT_FILES): $(SOURCE_FILES)
	javac $(SOURCE_FILES) -d $(BUILD_DIR)

run: $(OUTPUT_FILES)
	java -cp $(BUILD_DIR) Main

package: clean
	tar -czf build/package.tar --exclude=build ./*

images:
	tar -czf build/images.tar ./images

clean:
	rm -rf ./*.tar

config:
	git config --global user.email "project-people@team"
	git config --global user.name "replit bad"

pull: config
	git pull
	git