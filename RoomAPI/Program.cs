var builder = WebApplication.CreateBuilder(args);

// 添加服务到DI容器
builder.Services.AddControllers();
builder.WebHost.ConfigureKestrel(options =>
{
    options.ListenAnyIP(5004, listenOptions =>
    {
        listenOptions.UseHttps("D:/D_App4/apicert.pfx", "123456");
    });
    options.ListenAnyIP(5259); // HTTP endpoint
});
var app = builder.Build();

// 配置HTTP请求管道
if (app.Environment.IsDevelopment())
{
    app.UseDeveloperExceptionPage();
}

app.UseHttpsRedirection();
app.UseAuthorization();
app.MapControllers();

app.Run();
