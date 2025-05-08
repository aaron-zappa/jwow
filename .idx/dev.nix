{ pkgs, ... }: {
  channel = "stable-23.11";
  packages = [
    pkgs.zulu17
    pkgs.maven
  ];
  env = { };
  idx = {
    extensions = [
      "vscjava.vscode-java-pack"
      "rangav.vscode-thunder-client"
    ];
    workspace = {
      onCreate = {
        install = "mvn clean install";
      };
      onStart = {
        run-server = "PORT=3000 mvn spring-boot:run";
      };
    };
    previews = {
      enable = true;
      previews = {
        html = {
          command = [
            "java"
            "-m"
            "jdk.httpserver"
            "--directory"
            "."
            "--port"
            "$PORT"
          ];
          manager = "web";
          # Optionally, specify a directory if your HTML is not in root:
          # cwd = "public";
        };
      };
    };
  };
}
